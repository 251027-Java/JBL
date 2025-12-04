package com.revature.expensereport.dto;

import java.time.LocalDateTime;

public abstract class ExpenseDto {

    public record Standard(String id, LocalDateTime date, String merchant, double value, String reportId) {}

    public record NoReport(String id, LocalDateTime date, String merchant, double value) {}

    public record NoId(LocalDateTime date, String merchant, double value, String reportId) {}
}
