package com.example.expense.repository;

import com.example.expense.Expense;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class TextRepository implements IRepository {

    private String filename = "expenses.txt";

    @Override
    public void saveExpenses(List<Expense> expenses) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            out.println(expenses);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
