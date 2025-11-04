package com.example.expense.repository;

import com.example.expense.Expense;
import java.util.List;

public interface IRepository {
    List<Expense> loadExpenses();

    void saveExpenses(List<Expense> expenses);
}
