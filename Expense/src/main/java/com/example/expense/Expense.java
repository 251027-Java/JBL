package com.example.expense;

import java.time.LocalDateTime;

public class Expense {

    private int id;
    private LocalDateTime date;
    private double value;
    private String merchant;

    public Expense(int id, LocalDateTime date, double value, String merchant) {
        this.id = id;
        this.date = date;
        this.value = value;
        this.merchant = merchant;
    }

    public int getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getMerchant() {
        return merchant;
    }

    @Override
    public String toString() {
        return String.format(
                "%s [id=%d, date=%s, value=%f, merchant=%s]", getClass().getName(), id, date, value, merchant);
    }

    public String toCSV() {
        return String.format("%d,%s,%f,%s", id, date, value, merchant);
    }
}
