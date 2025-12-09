package com.revature.expensereport.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.revature.expensereport.dto.ExpenseDto;
import com.revature.expensereport.mapper.ExpenseMapper;
import com.revature.expensereport.model.Expense;
import com.revature.expensereport.repository.ExpenseRepository;
import com.revature.expensereport.repository.ReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTests {
    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private ExpenseMapper expenseMapper;

    @InjectMocks
    private ExpenseService service;

    @Test
    void happyPath_getById_returnsExpenseDto() {
        var date = LocalDateTime.now();
        Expense mockExpense = new Expense("id", "costco", date, 34, null);
        ExpenseDto.Standard expectedDto = new ExpenseDto.Standard("id", date, "costco", 34, null);

        when(expenseRepository.findById("id")).thenReturn(Optional.of(mockExpense));
        when(expenseMapper.toStandardDto(mockExpense)).thenReturn(expectedDto);

        ExpenseDto.Standard actual = service.getById("id");

        assertEquals(expectedDto, actual);
    }
}
