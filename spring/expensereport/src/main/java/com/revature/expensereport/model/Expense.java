package com.revature.expensereport.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.Optional;

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
    @JoinColumn(name = "report_id", foreignKey = @ForeignKey(name = "fk_report_id"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ToString.Exclude
    private Report report;

    public Expense(String merchant, LocalDateTime date, double value) {
        this.merchant = merchant;
        this.date = date;
        this.value = value;
    }

    public Expense(String merchant, LocalDateTime date, double value, Report report) {
        this.merchant = merchant;
        this.date = date;
        this.value = value;
        this.report = report;
    }

    @ToString.Include
    private String reportId() {
        return Optional.ofNullable(report).map(Report::getId).orElse(null);
    }
}
