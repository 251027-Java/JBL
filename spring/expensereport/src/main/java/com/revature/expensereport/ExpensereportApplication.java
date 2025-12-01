package com.revature.expensereport;

import com.revature.expensereport.model.Expense;
import com.revature.expensereport.repository.ExpenseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class ExpensereportApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpensereportApplication.class, args);
    }

    @Bean
    CommandLineRunner seedData(ExpenseRepository expenseRepository) {
        return args -> {
            var list = List.of(
                    new Expense(LocalDateTime.now(), "walmart", 2.45),
                    new Expense(LocalDateTime.now(), "starbucks", 35.35),
                    new Expense(LocalDateTime.now(), "buffalo wild wings", 99.65));

            expenseRepository.saveAllAndFlush(list);
        };
    }
}
