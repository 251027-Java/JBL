package com.example.expense.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.expense.Expense;
import com.example.expense.repository.IRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExpenseServiceTest {
    private ExpenseService service;
    private IRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(IRepository.class);
        service = new ExpenseService(repository);
    }

    @Test
    void addExpense() {
        Expense addedExpense = service.addExpense(100, "walmart");

        assertNotNull(addedExpense);
        assertEquals(1, addedExpense.getId());
        assertEquals(100, addedExpense.getValue());
    }

    @Test
    void addMultipleExpenses() {
        Expense e1 = service.addExpense(100, "walmart");

        when(repository.loadExpenses()).thenReturn(List.of(e1));
        Expense e2 = service.addExpense(12, "costco");

        assertNotNull(e1);
        assertEquals(1, e1.getId());
        assertEquals(100, e1.getValue());

        assertNotNull(e2);
        assertEquals(2, e2.getId());
        assertEquals(12, e2.getValue());
    }

    @Test
    void getExpense() {
        Expense mockExpense = new Expense(1, LocalDateTime.now(), 123, "walmart");
        when(repository.readExpense(1)).thenReturn(mockExpense);
        var e1 = service.getExpense(1);

        assertNotNull(e1);
        assertEquals(1, e1.getId());
        assertEquals(123, e1.getValue());
    }

    @Test
    void getNonexistantExpense() {
        var e1 = service.getExpense(1);

        assertNull(e1);
    }

    @Test
    void deleteExpense() {
        Expense mockExpense = new Expense(1, LocalDateTime.now(), 100, "walmart");
        when(repository.readExpense(1)).thenReturn(mockExpense);

        var e1 = service.deleteExpense(1);
        assertNotNull(e1);
        assertEquals(1, e1.getId());
        assertEquals(100, e1.getValue());
    }

    @Test
    void deleteNonexistantExpense() {
        when(repository.readExpense(1)).thenReturn(null);

        var e1 = service.deleteExpense(1);
        assertNull(e1);
    }

    @Test
    void updateExpense() {
        var e1 = new Expense(1, LocalDateTime.now(), 123, "walmart");
        service.updateExpense(e1);

        verify(repository, times(1)).updateExpense(e1);
    }

    @Test
    void sumExpenses() {
        when(repository.loadExpenses())
                .thenReturn(List.of(
                        new Expense(1, LocalDateTime.now(), 1, "costco"),
                        new Expense(1, LocalDateTime.now(), 2.5, "costco")));

        var sum = service.sumExpenses();

        assertEquals(3.5, sum);
    }

    @Test
    void sumZeroExpenses() {
        when(repository.loadExpenses()).thenReturn(List.of());

        var sum = service.sumExpenses();

        assertEquals(0, sum);
    }

    @Test
    void sumSingleExpense() {
        when(repository.loadExpenses()).thenReturn(List.of(new Expense(1, LocalDateTime.now(), 5, "walmart")));

        var sum = service.sumExpenses();

        assertEquals(5, sum);
    }
}
