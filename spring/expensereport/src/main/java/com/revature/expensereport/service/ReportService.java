package com.revature.expensereport.service;

import com.revature.expensereport.dto.ReportDto;
import com.revature.expensereport.dto.SimpleReportDto;
import com.revature.expensereport.model.Report;
import com.revature.expensereport.repository.ExpenseRepository;
import com.revature.expensereport.repository.ReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportService.class);
    private final ReportRepository reportRepository;
    private final ExpenseRepository expenseRepository;

    public ReportService(ReportRepository reportRepository, ExpenseRepository expenseRepository) {
        this.reportRepository = reportRepository;
        this.expenseRepository = expenseRepository;
    }

    public List<ReportDto> getAllReports() {
        var res = reportRepository.findAll();

        LOGGER.info(res.toString());

        return res.stream().map(this::toDto).toList();
    }

    public List<ReportDto> findByTitle(String title) {
        return reportRepository.findByTitle(title).stream().map(this::toDto).toList();
    }

    public ReportDto create(SimpleReportDto dto) {
        var e = reportRepository.save(new Report(dto.title(), dto.status()));
        return toDto(e);
    }

    public ReportDto getById(String id) {
        return reportRepository.findById(id).map(this::toDto).orElse(null);
    }

    public ReportDto update(String id, SimpleReportDto dto) {
        var ret = reportRepository
                .findById(id)
                .map(e -> {
                    e.setTitle(dto.title());
                    e.setStatus(dto.status());
                    return e;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return toDto(reportRepository.save(ret));
    }

    public void delete(String id) {

        reportRepository.deleteById(id);
    }

    private ReportDto toDto(Report report) {
        return new ReportDto(report.getId(), report.getTitle(), report.getStatus());
    }
}
