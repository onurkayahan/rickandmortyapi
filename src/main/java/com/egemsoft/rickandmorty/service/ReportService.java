package com.egemsoft.rickandmorty.service;

import com.egemsoft.rickandmorty.domain.report.ReportDto;
import com.egemsoft.rickandmorty.domain.report.Report;
import com.egemsoft.rickandmorty.domain.report.ReportType;
import com.egemsoft.rickandmorty.repository.ReportRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }



    public List<ReportDto> getReportDtos() {
        List<ReportDto> reportDtos = new ArrayList<>();
        for (ReportType reportType : ReportType.values()) {
            List<Report> reportsByReportType = getReportsByReportType(reportType);
            reportDtos.add(new ReportDto()
                    .setReportType(reportType)
                    .setReports(reportsByReportType)
                    .setRequestCount(reportsByReportType.size()));
        }
        return reportDtos;
    }

    public List<Report> getReportsByReportType(ReportType reportType) {
        return reportRepository.findAllByReportType(reportType, new Sort(Sort.Direction.ASC, "created"));
    }



}
