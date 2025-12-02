package com.revature.expensereport.controller;

import com.revature.expensereport.dto.ExpenseDto;
import com.revature.expensereport.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public List<ExpenseDto> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/search")
    public List<ExpenseDto> search(@RequestParam String merchant) {
        return expenseService.searchByMerchant(merchant);
    }

    @PostMapping
    public ExpenseDto create(@RequestBody ExpenseDto expenseDto) {
        return expenseService.create(expenseDto);
    }

    @GetMapping("/{id}")
    public ExpenseDto getById(@PathVariable String id) {
        return expenseService.getById(id);
    }
}
