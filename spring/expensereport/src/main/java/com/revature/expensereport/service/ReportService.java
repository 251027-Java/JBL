package com.revature.expensereport.service;

import com.revature.expensereport.dto.FullReportDto;
import com.revature.expensereport.dto.PartialExpenseDto;
import com.revature.expensereport.dto.ReportDto;
import com.revature.expensereport.dto.SimpleReportDto;
import com.revature.expensereport.model.Report;
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

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<FullReportDto> getAllReports() {
        var res = reportRepository.findAll();

        LOGGER.info(res.toString());

        return res.stream().map(this::toFuilReportDto).toList();
    }

    public List<ReportDto> findByTitle(String title) {
        return reportRepository.findByTitle(title).stream()
                .map(this::toReportDto)
                .toList();
    }

    public ReportDto create(SimpleReportDto dto) {
        var e = reportRepository.save(new Report(dto.title(), dto.status()));
        return toReportDto(e);
    }

    public FullReportDto getById(String id) {
        return reportRepository.findById(id).map(this::toFuilReportDto).orElse(null);
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
        return toReportDto(reportRepository.save(ret));
    }

    public void delete(String id) {

        reportRepository.deleteById(id);
    }

    private ReportDto toReportDto(Report report) {
        return new ReportDto(report.getId(), report.getTitle(), report.getStatus());
    }

    private FullReportDto toFuilReportDto(Report report) {
        var expenses = report.getExpenses().stream()
                .map(e -> new PartialExpenseDto(e.getId(), e.getDate(), e.getMerchant(), e.getValue()))
                .toList();
        return new FullReportDto(report.getId(), report.getTitle(), report.getStatus(), expenses);
    }
}
