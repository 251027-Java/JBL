package com.example.expense;

import com.example.expense.repository.IRepository;
import com.example.expense.repository.MongoRepository;
import com.example.expense.service.ExpenseService;
import com.mongodb.client.model.Filters;
import java.time.LocalDateTime;

public class Main {

    static void main() {
        IRepository repo = new MongoRepository();
        ExpenseService service = new ExpenseService(repo);

        System.out.println(service.getExpense(1));
        System.out.println(service.getExpense(2));

        service.addExpense(34.36, "walmart");
        service.updateExpense(new Expense(2, LocalDateTime.now(), 100, "walmart"));

        System.out.println(service.getExpense(1));
        System.out.println(service.getExpense(2));

        var s = Filters.and(Filters.eq("id", 1), Filters.regex("name", "^[0-9]d"));
        System.out.println(s);
    }
}
