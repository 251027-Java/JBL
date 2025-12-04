package com.revature.expensereport.dto;

import java.util.List;

public record FullReportDto(String id, String title, String status, List<PartialExpenseDto> expenses) {}
