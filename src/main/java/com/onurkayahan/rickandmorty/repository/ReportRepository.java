package com.onurkayahan.rickandmorty.repository;

import com.onurkayahan.rickandmorty.domain.report.Report;
import com.onurkayahan.rickandmorty.domain.report.ReportType;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends MongoRepository<Report, Integer> {

    List<Report> findAllByReportType(ReportType reportType, Sort sort);

}
