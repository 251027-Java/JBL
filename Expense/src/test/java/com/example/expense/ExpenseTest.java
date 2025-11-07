package com.example.expense;

import java.time.LocalDateTime;

public class ExpenseTest {
    static void main() {
        testExpenseCreation();
    }

    public static void testExpenseCreation() {
        // arrange
        Expense expense = new Expense(1, LocalDateTime.now(), 100, "DummyMerchant");

        // act
        int id = expense.getId();
        double value = expense.getValue();

        // assert
        if (id != 1) {
            System.out.println("Failed id");
        }

        if (value != 100) {
            System.out.println("Failed value");
        }
    }
}
