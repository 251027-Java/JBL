package com.revature.expensereport.repository;

import com.revature.expensereport.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, String> {
    List<Report> findByTitle(String title);
}
