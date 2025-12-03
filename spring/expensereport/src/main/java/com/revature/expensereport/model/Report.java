package com.revature.expensereport.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue
    private String id;

    private String status;
    private String title;

    @OneToMany(mappedBy = "report")
    private List<Expense> expenses = new ArrayList<>();

    public Report(String title, String status) {
        this.status = status;
        this.title = title;
    }
}
