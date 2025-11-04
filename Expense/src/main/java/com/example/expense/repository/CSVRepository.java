package com.example.expense.repository;

import com.example.expense.Expense;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CSVRepository implements IRepository {

    private String filename = "expenses.csv";

  @Override
  public void createExpense(Expense expense) {

  }

  @Override
  public Expense readExpense(int id) {
    return null;
  }

  @Override
  public void updateExpense(Expense expense) {

  }

  @Override
  public void deleteExpense(int id) {

  }

  @Override
    public List<Expense> loadExpenses() {
        List<Expense> ret = new ArrayList<>();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))) {
            in.readLine();
            String line;

            while ((line = in.readLine()) != null) {
                String[] parts = line.split(",");
                ret.add(new Expense(
                        Integer.parseInt(parts[0]),
                        LocalDateTime.parse(parts[1]),
                        Double.parseDouble(parts[2]),
                        parts[3]));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ret;
    }

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
