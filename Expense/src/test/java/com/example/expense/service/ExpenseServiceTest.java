package com.example.expense.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.expense.Expense;
import com.example.expense.repository.CSVRepository;
import com.example.expense.repository.IRepository;
import com.example.expense.repository.JSONRepository;
import com.example.expense.repository.TextRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ExpenseServiceTest {

    @BeforeEach
    void clearRepositories() {
        new TextRepository().saveExpenses(List.of());
        new CSVRepository().saveExpenses(List.of());
        new JSONRepository().saveExpenses(List.of());
    }

    static Stream<IRepository> repoProvider() {
        return Stream.of(new TextRepository(), new CSVRepository(), new JSONRepository());
    }

    @Test
    void addAndGetToRepository() {
        IRepository repo = mock(IRepository.class);
        ExpenseService service = new ExpenseService(repo);

        Expense mockExpense = new Expense(1, LocalDateTime.now(), 100, "walmart");
        when(repo.readExpense(1)).thenReturn(mockExpense);

        service.addExpense(100, "walmart");

        var expense = service.getExpense(1);
        assertEquals(1, expense.getId());
        assertEquals(100, expense.getValue());
    }

    @ParameterizedTest
    @MethodSource("repoProvider")
    void addAndGetMultipleToRepository(IRepository repo) {
        ExpenseService service = new ExpenseService(repo);

        service.addExpense(100, "walmart");
        service.addExpense(12, "costco");

        var e1 = service.getExpense(1);
        assertEquals(1, e1.getId());
        assertEquals(100, e1.getValue());

        var e2 = service.getExpense(2);
        assertEquals(2, e2.getId());
        assertEquals(12, e2.getValue());
    }

    @ParameterizedTest
    @MethodSource("repoProvider")
    void getNonexistantFromRepository(IRepository repo) {
        ExpenseService service = new ExpenseService(repo);
        var e1 = service.getExpense(1);
        assertNull(e1);
    }

    @ParameterizedTest
    @MethodSource("repoProvider")
    void deleteFromRepository(IRepository repo) {
        ExpenseService service = new ExpenseService(repo);

        service.addExpense(100, "walmart");
        service.deleteExpense(1);

        var e1 = service.getExpense(1);
        assertNull(e1);
    }

    @ParameterizedTest
    @MethodSource("repoProvider")
    void deleteNonexistantFromRepository(IRepository repo) {
        ExpenseService service = new ExpenseService(repo);

        assertDoesNotThrow(() -> {
            service.deleteExpense(1);
        });
    }

    @ParameterizedTest
    @MethodSource("repoProvider")
    void updateRepository(IRepository repo) {
        ExpenseService service = new ExpenseService(repo);
        service.addExpense(100, "walmart");

        service.updateExpense(new Expense(1, LocalDateTime.now(), 95, "walmart"));

        var e1 = service.getExpense(1);
        assertNotNull(e1);
        assertEquals(1, e1.getId());
        assertEquals(95, e1.getValue());
    }

    @ParameterizedTest
    @MethodSource("repoProvider")
    void updateNonexistantInRepository(IRepository repo) {
        ExpenseService service = new ExpenseService(repo);

        assertDoesNotThrow(() -> {
            service.updateExpense(new Expense(1, LocalDateTime.now(), 95, "walmart"));
        });

        var e1 = service.getExpense(1);
        assertNull(e1);
    }

    @ParameterizedTest
    @MethodSource("repoProvider")
    void calcSumsFromRepository(IRepository repo) {
        ExpenseService service = new ExpenseService(repo);

        service.addExpense(100, "walmart");
        service.addExpense(10, "walmart");

        var sum = service.sumExpenses();
        assertEquals(110, sum);
    }

    @ParameterizedTest
    @MethodSource("repoProvider")
    void calcZeroSumsFromRepository(IRepository repo) {
        ExpenseService service = new ExpenseService(repo);

        var sum = service.sumExpenses();
        assertEquals(0, sum);
    }
}
