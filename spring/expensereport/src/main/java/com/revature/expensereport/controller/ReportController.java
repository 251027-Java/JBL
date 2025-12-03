package com.revature.expensereport.controller;

import com.revature.expensereport.dto.ReportDto;
import com.revature.expensereport.dto.SimpleReportDto;
import com.revature.expensereport.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public List<ReportDto> getAllReports() {
        return reportService.getAllReports();
    }

    @GetMapping("/search")
    public List<ReportDto> search(@RequestParam String title) {
        return reportService.findByTitle(title);
    }

    @PostMapping
    public ReportDto create(@RequestBody SimpleReportDto dto) {
        return reportService.create(dto);
    }

    @GetMapping("/{id}")
    public ReportDto getById(@PathVariable String id) {
        return reportService.getById(id);
    }

    @PutMapping("/{id}")
    public ReportDto update(@PathVariable String id, @RequestBody SimpleReportDto dto) {
        return reportService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        reportService.delete(id);
    }
}
