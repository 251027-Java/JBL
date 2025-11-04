package com.example.expense;

import com.example.expense.repository.IRepository;
import com.example.expense.repository.TextRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static void main() {
        IRepository repo = new TextRepository();

        var list2 = repo.loadExpenses();
        System.out.println("Loaded list");
        System.out.println(list2);

        List<Expense> list = new ArrayList<>();

        list.add(new Expense(1, LocalDateTime.now(), 99.32, "Walmart"));
        list.add(new Expense(2, LocalDateTime.now(), 35.2, "Costco"));
        list.add(new Expense(3, LocalDateTime.now(), 10000, "Private Jet"));

        System.out.println("New list");
        System.out.println(list);
        repo.saveExpenses(list);
    }
}
