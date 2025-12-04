package com.revature.expensereport.service;

import com.revature.expensereport.dto.ExpenseDto;
import com.revature.expensereport.mapper.ExpenseMapper;
import com.revature.expensereport.repository.ExpenseRepository;
import com.revature.expensereport.repository.ReportRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    public List<ExpenseDto.Standard> getAllExpenses() {
        return expenseRepository.findAll().stream()
                .map(expenseMapper::toStandardDto)
                .toList();
    }

    public List<ExpenseDto.Standard> searchByMerchant(String merchant) {
        return expenseRepository.findByMerchant(merchant).stream()
                .map(expenseMapper::toStandardDto)
                .toList();
    }

    public ExpenseDto.Standard create(ExpenseDto.NoId expenseDto) {
        var entity = expenseRepository.save(expenseMapper.toEntity(expenseDto, reportRepository));
        return expenseMapper.toStandardDto(entity);
    }

    public ExpenseDto.Standard getById(String id) {
        return expenseRepository.findById(id).map(expenseMapper::toStandardDto).orElse(null);
    }

    public ExpenseDto.Standard update(String id, ExpenseDto.NoId dto) {
        var expense = expenseRepository
                .findById(id)
                .map(e -> {
                    var entity = expenseMapper.toEntity(dto, reportRepository);
                    entity.setId(e.getId());
                    return entity;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found"));

        return expenseMapper.toStandardDto(expenseRepository.save(expense));
    }

    public void delete(String id) {
        expenseRepository.deleteById(id);
    }
}
