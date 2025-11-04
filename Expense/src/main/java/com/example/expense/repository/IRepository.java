package com.example.expense.repository;

import com.example.expense.Expense;
import java.util.List;

public interface IRepository {
    void createExpense(Expense expense);

    Expense readExpense(int id);

    void updateExpense(Expense expense);

    void deleteExpense(int id);

    List<Expense> loadExpenses();

    void saveExpenses(List<Expense> expenses);
}
