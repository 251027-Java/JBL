package com.revature.expensereport.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Expense {
    @Id
    @GeneratedValue
    private String id;

    private String merchant;
    private LocalDateTime date;
    private double value;

    public Expense() {}

    public Expense(LocalDateTime date, String merchant, double value) {
        this.date = date;
        this.merchant = merchant;
        this.value = value;
    }
}
