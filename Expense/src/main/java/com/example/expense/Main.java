package com.example.expense;

import com.example.expense.repository.IRepository;
import com.example.expense.repository.MongoRepository;
import com.example.expense.service.ExpenseService;

public class Main {

    static void main() {
        IRepository repo = new MongoRepository();
        ExpenseService service = new ExpenseService(repo);

        System.out.println(service.getExpense(1));
        System.out.println(service.getExpense(2));

        service.addExpense(34.36, "walmart");

        System.out.println(service.getExpense(1));
        System.out.println(service.getExpense(2));
    }
}
