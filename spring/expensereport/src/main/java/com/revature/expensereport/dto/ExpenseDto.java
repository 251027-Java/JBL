package com.revature.expensereport.dto;

import java.time.LocalDateTime;

public record ExpenseDto(String id, LocalDateTime date, String merchant, double value, String reportId) {}
