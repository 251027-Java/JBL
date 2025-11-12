package com.example.expense.repository;

import com.example.expense.Expense;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.bson.Document;

public class MongoRepository implements IRepository {

    private final String user = "mongoadmin";
    private final String password = "secret";
    private final String hostname = "localhost";
    private final int port = 27017;

    private final String uri = String.format("mongodb://%s:%s@%s:%d", user, password, hostname, port);

    private MongoClient client;
    private MongoDatabase db;
    private MongoCollection collection ;

    public MongoRepository() {
      System.out.println(uri);
        client = MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .applyToSocketSettings(builder -> builder.connectTimeout(5L, TimeUnit.SECONDS))
                .build());

        db = client.getDatabase("expensesdb");
        collection = db.getCollection("expense");
    }

    @Override
    public void createExpense(Expense expense) {
//      collection.insertOne(new Document("id",expense.getId()).append("date",expense.getDate().toString()).append(""))
        IRepository.super.createExpense(expense);
    }

    @Override
    public Expense readExpense(int id) {
        return IRepository.super.readExpense(id);
    }

    @Override
    public void updateExpense(Expense expense) {
        IRepository.super.updateExpense(expense);
    }

    @Override
    public void deleteExpense(int id) {
        IRepository.super.deleteExpense(id);
    }

    @Override
    public List<Expense> loadExpenses() {
        return List.of();
    }

    @Override
    public void saveExpenses(List<Expense> expenses) {}
}
