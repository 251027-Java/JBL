package com.revature.expensereport.service;

import com.revature.expensereport.dto.ReportDto;
import com.revature.expensereport.dto.SimpleReportDto;
import com.revature.expensereport.model.Report;
import com.revature.expensereport.repository.ReportRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReportService {
    private final ReportRepository repository;

    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    public List<ReportDto> getAllReports() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    public List<ReportDto> findByTitle(String title) {
        return repository.findByTitle(title).stream().map(this::toDto).toList();
    }

    public ReportDto create(SimpleReportDto dto) {
        var e = repository.save(new Report(dto.title(), dto.status()));
        return toDto(e);
    }

    public ReportDto getById(String id) {
        return repository.findById(id).map(this::toDto).orElse(null);
    }

    public ReportDto update(String id, SimpleReportDto dto) {
        var ret = repository
                .findById(id)
                .map(e -> {
                    e.setTitle(dto.title());
                    e.setStatus(dto.status());
                    return e;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return toDto(repository.save(ret));
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    private ReportDto toDto(Report report) {
        return new ReportDto(report.getId(), report.getTitle(), report.getStatus());
    }
}
