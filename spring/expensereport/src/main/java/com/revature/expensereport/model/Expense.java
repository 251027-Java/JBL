package com.revature.expensereport.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
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
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ToString.Exclude
    private Report report;

    public Expense(LocalDateTime date, String merchant, double value) {
        this.date = date;
        this.merchant = merchant;
        this.value = value;
    }
}
