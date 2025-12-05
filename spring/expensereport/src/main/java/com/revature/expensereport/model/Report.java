package com.revature.expensereport.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue
    private String id;

    private String status;
    private String title;

    @OneToMany(mappedBy = "report")
    @ToString.Exclude
    private List<Expense> expenses = new ArrayList<>();

    public Report(String title, String status) {
        this.status = status;
        this.title = title;
    }
}
