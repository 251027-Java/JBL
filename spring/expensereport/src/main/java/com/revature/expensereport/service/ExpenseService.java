package com.revature.expensereport.service;

import com.revature.expensereport.dto.ExpenseDto;
import com.revature.expensereport.dto.SimpleExpenseDto;
import com.revature.expensereport.model.Expense;
import com.revature.expensereport.repository.ExpenseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<ExpenseDto> getAllExpenses() {
        return expenseRepository.findAll().stream().map(this::toDto).toList();
    }

    public List<ExpenseDto> searchByMerchant(String merchant) {
        return expenseRepository.findByMerchant(merchant).stream()
                .map(this::toDto)
                .toList();
    }

    public ExpenseDto create(SimpleExpenseDto expenseDto) {
        var entity = expenseRepository.save(new Expense(expenseDto.date(), expenseDto.merchant(), expenseDto.value()));
        return toDto(entity);
    }

    public ExpenseDto getById(String id) {
        return expenseRepository.findById(id).map(this::toDto).orElse(null);
    }

    public ExpenseDto update(String id, SimpleExpenseDto dto) {
        var expense = expenseRepository
                .findById(id)
                .map(e -> {
                    e.setDate(dto.date());
                    e.setMerchant(dto.merchant());
                    e.setValue(dto.value());

                    return e;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return toDto(expenseRepository.save(expense));
    }

    public void delete(String id) {
        expenseRepository.deleteById(id);
    }

    private ExpenseDto toDto(Expense expense) {
        return new ExpenseDto(expense.getId(), expense.getDate(), expense.getMerchant(), expense.getValue());
    }
}
