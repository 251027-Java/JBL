package com.example.expense;

import com.example.expense.repository.JSONRepository;
import com.example.expense.service.ExpenseService;

public class Main {

    static void main() {
        ExpenseService service = new ExpenseService(new JSONRepository());
        service.start();
    }
}
