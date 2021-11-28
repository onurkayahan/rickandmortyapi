package com.onurkayahan.rickandmorty.domain.character;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class Origin {

    private String name;
    private String url;

}
