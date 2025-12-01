package com.revature.expensereport.model;

import jakarta.persistence.Column;
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

    @Column(name = "price")
    private double value;

    public Expense() {}

    public Expense(LocalDateTime date, String merchant, double value) {
        this.date = date;
        this.merchant = merchant;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
