package com.revature.expensereport;

import com.revature.expensereport.model.Expense;
import com.revature.expensereport.model.Report;
import com.revature.expensereport.model.User;
import com.revature.expensereport.repository.ExpenseRepository;
import com.revature.expensereport.repository.ReportRepository;
import com.revature.expensereport.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class ExpensereportApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpensereportApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ExpensereportApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner seedData(
            ExpenseRepository expenseRepository,
            UserRepository userRepository,
            ReportRepository reportRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            var reports = List.of(new Report("texas", "complete"));

            if (reportRepository.count() == 0) {
                reportRepository.saveAll(reports);
            }

            if (expenseRepository.count() == 0) {
                expenseRepository.saveAll(List.of(
                        new Expense("walmart", LocalDateTime.now(), 2.45, reports.getFirst()),
                        new Expense("starbucks", LocalDateTime.now(), 35.35),
                        new Expense("buffalo wild wings", LocalDateTime.now(), 99.65)));
            }

            if (userRepository.count() == 0) {
                String adminpass = passwordEncoder.encode("password");
                LOGGER.info("admin password: {}", adminpass);

                userRepository.saveAll(List.of(
                        new User("admin", adminpass, "ADMIN"),
                        new User("user", passwordEncoder.encode("secret"), "USER")));
            }
        };
    }
}
