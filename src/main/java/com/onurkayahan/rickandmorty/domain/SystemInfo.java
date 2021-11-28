package com.onurkayahan.rickandmorty.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Accessors(chain = true)
@Document(collection = "SystemInfo")
@Getter
@Setter
public class SystemInfo {

    @Id
    private Integer id;
    private Date lastUpdatedDateOfDb;
}
