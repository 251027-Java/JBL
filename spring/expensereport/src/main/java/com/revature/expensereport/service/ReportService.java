package com.revature.expensereport.service;

import com.revature.expensereport.dto.ReportDto;
import com.revature.expensereport.mapper.ReportMapper;
import com.revature.expensereport.repository.ReportRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    public ReportService(ReportRepository reportRepository, ReportMapper reportMapper) {
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
    }

    public List<ReportDto.WithExpenses> getAllReports() {
        return reportRepository.findAll().stream().map(reportMapper::toBigDto).toList();
    }

    public List<ReportDto.Standard> findByTitle(String title) {
        return reportRepository.findByTitle(title).stream()
                .map(reportMapper::toStandardDto)
                .toList();
    }

    public ReportDto.Standard create(ReportDto.NoId dto) {
        var e = reportRepository.save(reportMapper.toEntity(dto));
        return reportMapper.toStandardDto(e);
    }

    public ReportDto.WithExpenses getById(String id) {
        return reportRepository.findById(id).map(reportMapper::toBigDto).orElse(null);
    }

    public ReportDto.Standard update(String id, ReportDto.NoId dto) {
        var ret = reportRepository
                .findById(id)
                .map(e -> {
                    e.setTitle(dto.title());
                    e.setStatus(dto.status());
                    return e;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return reportMapper.toStandardDto(reportRepository.save(ret));
    }

    public void delete(String id) {
        reportRepository.deleteById(id);
    }
}
