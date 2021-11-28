package com.onurkayahan.rickandmorty.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Accessors(chain = true)
@Getter
@Setter
public class Base {

    @Id
    private Integer id;
    private String name;
    private String url;
    private Date created;
}
