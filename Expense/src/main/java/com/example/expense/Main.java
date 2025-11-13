package com.example.expense;

import com.example.expense.repository.IRepository;
import com.example.expense.repository.MongoRepository;
import com.example.expense.service.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    static void main() {
        log.info("Starting expense tracker");

        IRepository repo = new MongoRepository();
        log.info("Repository created");

        ExpenseService service = new ExpenseService(repo);
        log.info("Services created");

        service.printExpenses();
        System.out.println(service.sumExpenses());

        log.info("Ending expense tracker");
    }
}
