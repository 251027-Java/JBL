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
import java.util.regex.Pattern;

public class TextRepository implements IRepository {

    private String filename = "expenses.txt";

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
            String line = in.readLine();

            Pattern pat = Pattern.compile("=([^,\\]]+)");
            var matcher = pat.matcher(line);
            var list = matcher.results().map(e -> e.group(1)).toList();

            int fieldCount = 4;
            for (int i = 0; i < list.size(); i += fieldCount) {
                ret.add(new Expense(
                        Integer.parseInt(list.get(i)),
                        LocalDateTime.parse(list.get(i + 1)),
                        Double.parseDouble(list.get(i + 2)),
                        list.get(i + 3)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ret;
    }

    @Override
    public void saveExpenses(List<Expense> expenses) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            out.println(expenses);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
