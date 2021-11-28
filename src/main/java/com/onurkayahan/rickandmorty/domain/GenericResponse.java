package com.onurkayahan.rickandmorty.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Getter
@Setter
public class GenericResponse<T> {

    private Info info;
    private List<T> results;

}
