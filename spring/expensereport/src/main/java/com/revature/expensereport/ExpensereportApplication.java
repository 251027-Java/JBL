package com.revature.expensereport;

import com.revature.expensereport.model.Expense;
import com.revature.expensereport.model.Report;
import com.revature.expensereport.model.User;
import com.revature.expensereport.repository.ExpenseRepository;
import com.revature.expensereport.repository.ReportRepository;
import com.revature.expensereport.repository.UserRepository;
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
    CommandLineRunner seedData(
            ExpenseRepository expenseRepository, UserRepository userRepository, ReportRepository reportRepository) {
        return args -> {
            var reports = List.of(new Report("texas", "complete"));
            reportRepository.saveAll(reports);

            expenseRepository.saveAll(List.of(
                    new Expense("walmart", LocalDateTime.now(), 2.45, reports.getFirst()),
                    new Expense("starbucks", LocalDateTime.now(), 35.35),
                    new Expense("buffalo wild wings", LocalDateTime.now(), 99.65)));

            userRepository.saveAll(List.of(new User("admin", "password", "ADMIN"), new User("user", "secret", "USER")));
        };
    }
}
