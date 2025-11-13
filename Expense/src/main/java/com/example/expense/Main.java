package com.example.expense;

import com.example.expense.repository.IRepository;
import com.example.expense.repository.MongoRepository;
import com.example.expense.service.ExpenseService;

public class Main {

    static void main() {
        IRepository repo = new MongoRepository();
        ExpenseService service = new ExpenseService(repo);

        service.printExpenses();
        System.out.println(service.sumExpenses());
    }
}
