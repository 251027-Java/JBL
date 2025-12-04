package com.revature.expensereport.dto;

import java.util.List;

public abstract class ReportDto {

    public record Standard(String id, String title, String status) {}

    public record NoId(String title, String status) {}

    public record WithExpenses(String id, String title, String status, List<ExpenseDto.NoReport> expenses) {}
}
