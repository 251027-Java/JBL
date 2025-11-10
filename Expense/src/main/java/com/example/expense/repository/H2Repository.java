package com.example.expense.repository;

import com.example.expense.Expense;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class H2Repository implements IRepository {

    private static final String H2_URL = "jdbc:h2:mem:expenses;DB_CLOSE_DELAY=-1";
    private Connection connection;

    public H2Repository() {
        try {
            Class.forName("org.h2.Driver");

            connection = DriverManager.getConnection(H2_URL);

            String sql = """
                CREATE SCHEMA IF NOT EXISTS ExpenseReport;

                CREATE TABLE IF NOT EXISTS ExpenseReport.Expenses (
                  id INT PRIMARY KEY,
                  date TIMESTAMP NOT NULL,
                  value FLOAT CHECK (value > 0),
                  merchant VARCHAR(50) NOT NULL
                );
                """;

            connection.createStatement().executeQuery(sql);

            System.out.println("Made H2 database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createExpense(Expense expense) {
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
