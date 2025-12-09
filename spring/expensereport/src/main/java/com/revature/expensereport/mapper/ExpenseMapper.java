package com.revature.expensereport.mapper;

import com.revature.expensereport.dto.ExpenseDto;
import com.revature.expensereport.model.Expense;
import com.revature.expensereport.model.Report;
import com.revature.expensereport.repository.ReportRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    @Mapping(target = "reportId", source = "report.id")
    ExpenseDto.Standard toStandardDto(Expense expense);

    ExpenseDto.NoReport toSmallDto(Expense expense);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "report", source = "reportId")
    Expense toEntity(ExpenseDto.NoId dto, @Context ReportRepository reportRepository);

    default Report map(String id, @Context ReportRepository reportRepository) {
        if (id == null) return null;

        return reportRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "report not found"));
    }
}
