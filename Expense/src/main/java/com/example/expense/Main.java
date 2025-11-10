package com.example.expense;

import com.example.expense.repository.IRepository;
import com.example.expense.repository.PostgreSQLRepository;
import com.example.expense.service.ExpenseService;

public class Main {

    static void main() {
        IRepository repo = new PostgreSQLRepository();
        ExpenseService service = new ExpenseService(repo);
    }
}
