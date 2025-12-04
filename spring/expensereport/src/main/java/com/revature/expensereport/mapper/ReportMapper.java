package com.revature.expensereport.mapper;

import com.revature.expensereport.dto.ReportDto;
import com.revature.expensereport.model.Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ExpenseMapper.class)
public interface ReportMapper {
    ReportDto.Standard toStandardDto(Report report);

    ReportDto.WithExpenses toBigDto(Report report);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    Report toEntity(ReportDto.NoId dto);
}
