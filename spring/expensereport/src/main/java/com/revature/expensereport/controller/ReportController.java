package com.revature.expensereport.controller;

import com.revature.expensereport.dto.ReportDto;
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
    public List<ReportDto.WithExpenses> getAllReports() {
        return reportService.getAllReports();
    }

    @GetMapping("/search")
    public List<ReportDto.Standard> search(@RequestParam String title) {
        return reportService.findByTitle(title);
    }

    @PostMapping
    public ReportDto.Standard create(@RequestBody ReportDto.NoId dto) {
        return reportService.create(dto);
    }

    @GetMapping("/{id}")
    public ReportDto.WithExpenses getById(@PathVariable String id) {
        return reportService.getById(id);
    }

    @PutMapping("/{id}")
    public ReportDto.Standard update(@PathVariable String id, @RequestBody ReportDto.NoId dto) {
        return reportService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        reportService.delete(id);
    }
}
