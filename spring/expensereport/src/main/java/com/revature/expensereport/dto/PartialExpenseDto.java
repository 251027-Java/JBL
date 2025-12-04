package com.revature.expensereport.dto;

import java.time.LocalDateTime;

public record PartialExpenseDto(String id, LocalDateTime date, String merchant, double value) {}
