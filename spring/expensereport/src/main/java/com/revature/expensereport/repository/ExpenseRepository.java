package com.revature.expensereport.repository;

import com.revature.expensereport.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, String> {
    List<Expense> findByMerchant(String merchant);
}
