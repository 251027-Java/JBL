package com.example.expense.repository;

import com.example.expense.Expense;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVRepository implements IRepository {

    private String filename = "expenses.csv";

    @Override
    public void saveExpenses(List<Expense> expenses) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            out.println("id,date,value,merchant");
            for (var e : expenses) {
                out.println(e.toCSV());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
