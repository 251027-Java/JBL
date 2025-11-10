package com.example.expense.service;

import com.example.expense.Expense;
import com.example.expense.repository.IRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ExpenseService {
    private IRepository repository;

    public ExpenseService(IRepository repository) {
        this.repository = repository;
    }

    private int generateUniqueID(List<Expense> expenseList) {
        Set<Integer> set = expenseList.stream().map(Expense::getId).collect(Collectors.toSet());

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            if (!set.contains(i)) {
                return i;
            }
        }

        return -1;
    }

    public Expense addExpense(double value, String merchant) {
        Expense expense =
                new Expense(generateUniqueID(repository.loadExpenses()), LocalDateTime.now(), value, merchant);
        repository.createExpense(expense);
        return expense;
    }

    public Expense deleteExpense(int id) {
        Expense expense = getExpense(id);
        repository.deleteExpense(id);
        return expense;
    }

    public void updateExpense(Expense expense) {
        repository.updateExpense(expense);
    }

    public Expense getExpense(int id) {
        return repository.readExpense(id);
    }

    public double sumExpenses() {
        return repository.loadExpenses().stream().mapToDouble(Expense::getValue).sum();
    }
}
