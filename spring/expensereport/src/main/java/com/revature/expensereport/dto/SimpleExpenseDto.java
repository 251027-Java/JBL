package com.revature.expensereport.dto;

import java.time.LocalDateTime;

public record SimpleExpenseDto(LocalDateTime date, String merchant, double value, String reportId) {}
