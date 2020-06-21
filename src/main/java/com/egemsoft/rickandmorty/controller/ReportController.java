package com.egemsoft.rickandmorty.controller;

import com.egemsoft.rickandmorty.domain.report.ReportDto;
import com.egemsoft.rickandmorty.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public List<ReportDto> getReportDto() {
        return reportService.getReportDtos();
    }

}
