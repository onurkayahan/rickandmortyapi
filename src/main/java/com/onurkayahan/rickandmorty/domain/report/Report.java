package com.onurkayahan.rickandmorty.domain.report;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Accessors(chain = true)
@Document(collection = "Report")
@Getter
@Setter
public class Report{

    @Id
    private Integer id;
    private ReportType reportType;
    private List<String> httpHeaders;
    private String body;
    @CreatedDate
    private Date created;
}
