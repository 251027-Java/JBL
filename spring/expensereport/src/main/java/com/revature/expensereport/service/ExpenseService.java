package com.revature.expensereport.service;

import com.revature.expensereport.dto.ExpenseDto;
import com.revature.expensereport.dto.SimpleExpenseDto;
import com.revature.expensereport.model.Expense;
import com.revature.expensereport.model.Report;
import com.revature.expensereport.repository.ExpenseRepository;
import com.revature.expensereport.repository.ReportRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ReportRepository reportRepository;

    public ExpenseService(ExpenseRepository expenseRepository, ReportRepository reportRepository) {
        this.expenseRepository = expenseRepository;
        this.reportRepository = reportRepository;
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
        var report = Optional.ofNullable(dto.reportId())
                .map(e -> reportRepository
                        .findById(e)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Report not found")))
                .orElse(null);

        var expense = expenseRepository
                .findById(id)
                .map(e -> {
                    e.setDate(dto.date());
                    e.setMerchant(dto.merchant());
                    e.setValue(dto.value());
                    e.setReport(report);

                    return e;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found"));

        return toDto(expenseRepository.save(expense));
    }

    public void delete(String id) {
        expenseRepository.deleteById(id);
    }

    private ExpenseDto toDto(Expense expense) {
        return new ExpenseDto(
                expense.getId(),
                expense.getDate(),
                expense.getMerchant(),
                expense.getValue(),
                Optional.ofNullable(expense.getReport()).map(Report::getId).orElse(null));
    }
}
