package com.example.expense.repository;

import com.example.expense.Expense;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class H2Repository implements IRepository {

    private static final String H2_URL = "jdbc:h2:mem:expenses;DB_CLOSE_DELAY=-1";
    private Connection connection;

    public H2Repository() {
        try {
            connection = DriverManager.getConnection(H2_URL);

            try (var st = connection.createStatement()) {
                st.execute("""
                    CREATE SCHEMA IF NOT EXISTS expense_report;

                    CREATE TABLE IF NOT EXISTS expenses (
                      id INT PRIMARY KEY,
                      date TIMESTAMP NOT NULL,
                      price DOUBLE CHECK (price > 0),
                      merchant VARCHAR(50) NOT NULL
                    );
                    """);
            }

            System.out.println("Made H2 database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createExpense(Expense expense) {
        try (var st = connection.prepareStatement("""
            INSERT INTO expenses (id, date, price, merchant) VALUES (?, ?, ?, ?);
            """)) {

            st.setInt(1, expense.getId());
            st.setTimestamp(2, Timestamp.valueOf(expense.getDate()));
            st.setDouble(3, expense.getValue());
            st.setString(4, expense.getMerchant());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Expense readExpense(int id) {
        try (var st = connection.prepareStatement("SELECT date, price, merchant FROM expenses WHERE id = ?"); ) {
            st.setInt(1, id);

            var res = st.executeQuery();

            LocalDateTime date = res.getTimestamp(1).toLocalDateTime();
            double price = res.getDouble(2);
            String merchant = res.getString(3);

            return new Expense(id, date, price, merchant);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateExpense(Expense expense) {
        try (var st = connection.prepareStatement("""
            UPDATE expenses
            SET
              value = ?,
              merchant = ?
            WHERE
              id = ?
            """)) {
            st.setDouble(1, expense.getValue());
            st.setString(2, expense.getMerchant());
            st.setInt(3, expense.getId());

            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteExpense(int id) {
        try (var statement = connection.prepareStatement("""
            DELETE FROM expenses
            WHERE
              id = ?
            """)) {
            statement.setInt(1, id);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Expense> loadExpenses() {
        List<Expense> ret = new ArrayList<>();

        try (var st = connection.createStatement()) {
            var result = st.executeQuery("SELECT id, date, price, merchant FROM expenses");

            while (result.next()) {
                int id = result.getInt("id");
                LocalDateTime date = result.getTimestamp("date").toLocalDateTime();
                double price = result.getDouble("price");
                String merchant = result.getString("merchant");

                ret.add(new Expense(id, date, price, merchant));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    @Override
    public void saveExpenses(List<Expense> expenses) {}
}
