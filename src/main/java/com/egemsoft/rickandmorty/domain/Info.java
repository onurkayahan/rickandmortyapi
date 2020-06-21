package com.egemsoft.rickandmorty.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class Info {

    private Long count;
    private Integer pages;
    private String next;
    private String prev;


}
