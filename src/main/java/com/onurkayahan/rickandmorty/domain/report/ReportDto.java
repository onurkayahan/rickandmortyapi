package com.onurkayahan.rickandmorty.domain.report;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Getter
@Setter
public class ReportDto {

    private ReportType reportType;

    private List<Report> reports;

    private Integer requestCount;

}
