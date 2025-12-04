package com.revature.expensereport.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "expense")
public class Expense {
    @Id
    @GeneratedValue
    private String id;

    private String merchant;
    private LocalDateTime date;

    @Column(name = "price")
    private double value;

    @ManyToOne
    @JoinColumn(name = "report_id")
    @ToString.Exclude
    private Report report;

    public Expense() {}

    public Expense(LocalDateTime date, String merchant, double value) {
        this.date = date;
        this.merchant = merchant;
        this.value = value;
    }
}
