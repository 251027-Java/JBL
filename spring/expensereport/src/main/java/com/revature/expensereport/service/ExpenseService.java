package com.revature.expensereport.service;

import com.revature.expensereport.model.Expense;
import com.revature.expensereport.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public List<Expense> searchByMerchant(String merchant) {
        return expenseRepository.findByMerchant(merchant);
    }
}
