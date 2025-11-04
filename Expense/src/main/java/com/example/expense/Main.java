package com.example.expense;

import com.example.expense.repository.CSVRepository;
import com.example.expense.repository.JSONRepository;
import com.example.expense.repository.TextRepository;

public class Main {

    static void main() {
        ExpenseService service = new ExpenseService(new TextRepository(), new CSVRepository(), new JSONRepository());
        service.start();
    }
}
