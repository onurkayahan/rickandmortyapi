package com.egemsoft.rickandmorty.domain.episode;

import com.egemsoft.rickandmorty.domain.Base;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
@Document(collection = "Episode")
@Getter
@Setter
public class Episode extends Base {

    @JsonProperty("air_date")
    private String airDate;

    private String episode;

    private List<String> characters;

    public List<String> getCharacters() {
        if (characters == null)
            return new ArrayList<>();
        return characters;
    }

}
