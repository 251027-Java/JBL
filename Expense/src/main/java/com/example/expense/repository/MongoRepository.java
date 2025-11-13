package com.example.expense.repository;

import com.example.expense.Expense;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class MongoRepository implements IRepository {

    private final String user = "mongoadmin";
    private final String password = "secret";
    private final String hostname = "localhost";
    private final int port = 27017;

    private final String uri = String.format("mongodb://%s:%s@%s:%d", user, password, hostname, port);

    private MongoClient client;
    private final MongoCollection<Document> collection;

    public MongoRepository() {
        client = MongoClients.create(uri);
        MongoDatabase db = client.getDatabase("expensesdb");
        collection = db.getCollection("expense");
    }

    private static Document toDocument(Expense e) {
        if (e == null) return null;

        return new Document("_id", e.getId())
                .append("date", e.getDate())
                .append("value", e.getValue())
                .append("merchant", e.getMerchant());
    }

    private static Expense toExpense(Document e) {
        if (e == null) return null;

        return new Expense(
                e.getInteger("_id"),
                e.getDate("date").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                e.getDouble("value"),
                e.getString("merchant"));
    }

    @Override
    public void createExpense(Expense expense) {
        collection.insertOne(toDocument(expense));
    }

    @Override
    public Expense readExpense(int id) {
        return toExpense(collection.find(Filters.eq("_id", id)).first());
    }

    @Override
    public void updateExpense(Expense expense) {
        collection.updateOne(
                Filters.eq("_id", expense.getId()),
                Updates.combine(
                        Updates.set("date", expense.getDate()),
                        Updates.set("value", expense.getValue()),
                        Updates.set("merchant", expense.getMerchant())),
                new UpdateOptions().upsert(true));
    }

    @Override
    public void deleteExpense(int id) {
        collection.deleteOne(Filters.eq("_id", id));
    }

    @Override
    public List<Expense> loadExpenses() {
        return collection.find().map(MongoRepository::toExpense).into(new ArrayList<>());
    }

    @Override
    public void saveExpenses(List<Expense> expenses) {
        expenses.forEach(this::updateExpense);
    }
}
