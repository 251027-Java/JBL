package com.revature.expensereport.service;

import com.revature.expensereport.dto.ExpenseDto;
import com.revature.expensereport.dto.SimpleExpenseDto;
import com.revature.expensereport.mapper.ExpenseMapper;
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
    private final ExpenseMapper expenseMapper;

    public ExpenseService(
            ExpenseRepository expenseRepository, ReportRepository reportRepository, ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.reportRepository = reportRepository;
        this.expenseMapper = expenseMapper;
    }

    public List<ExpenseDto> getAllExpenses() {
        return expenseRepository.findAll().stream()
                .map(expenseMapper::toExpenseDto)
                .toList();
    }

    public List<ExpenseDto> searchByMerchant(String merchant) {
        return expenseRepository.findByMerchant(merchant).stream()
                .map(expenseMapper::toExpenseDto)
                .toList();
    }

    public ExpenseDto create(SimpleExpenseDto expenseDto) {
        // TODO handle creating with report id?
        var entity = expenseRepository.save(expenseMapper.toEntity(expenseDto, reportRepository));
        return expenseMapper.toExpenseDto(entity);
    }

    public ExpenseDto getById(String id) {
        return expenseRepository.findById(id).map(expenseMapper::toExpenseDto).orElse(null);
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

        return expenseMapper.toExpenseDto(expenseRepository.save(expense));
    }

    public void delete(String id) {
        expenseRepository.deleteById(id);
    }
}
