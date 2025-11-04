package com.example.expense.repository;

import com.example.expense.Expense;
import java.util.List;

public interface IRepository {
    default void createExpense(Expense expense) {
        var arr = loadExpenses();
        arr.add(expense);
        saveExpenses(arr);
    }

    default Expense readExpense(int id) {
        return loadExpenses().stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    default void updateExpense(Expense expense) {
        var arr = loadExpenses().stream()
                .map(e -> e.getId() == expense.getId() ? expense : e)
                .toList();
        saveExpenses(arr);
    }

    default void deleteExpense(int id) {
        var arr = loadExpenses().stream().filter(e -> e.getId() != id).toList();
        saveExpenses(arr);
    }

    List<Expense> loadExpenses();

    void saveExpenses(List<Expense> expenses);
}
