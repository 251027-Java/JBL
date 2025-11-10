package com.example.expense;

import com.example.expense.repository.H2Repository;
import com.example.expense.repository.IRepository;
import com.example.expense.service.ExpenseService;

public class Main {

    static void main() {
        IRepository repo = new H2Repository();
        ExpenseService service = new ExpenseService(repo);
    }
}
