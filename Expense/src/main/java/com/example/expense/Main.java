package com.example.expense;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static void main() {
        List<Expense> list = new ArrayList<>();

        list.add(new Expense(1, LocalDateTime.now(), 99.32, "Walmart"));
        list.add(new Expense(2, LocalDateTime.now(), 35.2, "Costco"));
        list.add(new Expense(3, LocalDateTime.now(), 10000, "Private Jet"));

        System.out.println(list);
    }
}
