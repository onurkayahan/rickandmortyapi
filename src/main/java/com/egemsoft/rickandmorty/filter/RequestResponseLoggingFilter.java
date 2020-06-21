package com.egemsoft.rickandmorty.filter;

import com.egemsoft.rickandmorty.domain.report.Report;
import com.egemsoft.rickandmorty.domain.report.ReportType;
import com.egemsoft.rickandmorty.repository.ReportRepository;
import com.egemsoft.rickandmorty.service.ReportService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@Order(2)
public class RequestResponseLoggingFilter implements Filter {

    @Autowired
    private ReportRepository reportRepository;

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String requestBody = IOUtils.toString(request.getReader());

        saveRequestAndBodyAsReport(req, requestBody);
        chain.doFilter(request, response);

    }

    private void saveRequestAndBodyAsReport(HttpServletRequest request, String body) {

        ReportType reportType = getReportTypeFromRequest(request);
        if (reportType == null) {
            return;
        }

        Enumeration<String> headerNames = request.getHeaderNames();
        List<String> headers = new ArrayList<>();

        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                    headers.add(request.getHeader(headerNames.nextElement()));
            }
        }


        Report report = new Report()
                .setReportType(reportType)
                .setHttpHeaders(headers)
                .setBody(body);

        report.setCreated(new Date());
        report.setId(new Random().nextInt());
        reportRepository.save(report);
    }

    private ReportType getReportTypeFromRequest(HttpServletRequest request) {
        if (request.getRequestURI().contains("character")) {
            return ReportType.CHARACTER;
        } else if (request.getRequestURI().contains("location")) {
            return ReportType.LOCATION;
        } else if (request.getRequestURI().contains("episode")) {
            return ReportType.EPISODE;
        } else
            return null;
    }
}