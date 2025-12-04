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
    public List<ExpenseDto.Standard> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/search")
    public List<ExpenseDto.Standard> search(@RequestParam String merchant) {
        return expenseService.searchByMerchant(merchant);
    }

    @PostMapping
    public ExpenseDto.Standard create(@RequestBody ExpenseDto.NoId dto) {
        return expenseService.create(dto);
    }

    @GetMapping("/{id}")
    public ExpenseDto.Standard getById(@PathVariable String id) {
        return expenseService.getById(id);
    }

    @PutMapping("/{id}")
    public ExpenseDto.Standard update(@PathVariable String id, @RequestBody ExpenseDto.NoId dto) {
        return expenseService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        expenseService.delete(id);
    }
}
