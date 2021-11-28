package com.onurkayahan.rickandmorty.domain.character;

import com.onurkayahan.rickandmorty.domain.Base;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
@Document(collection = "Character")
@Getter
@Setter
public class Character extends Base {

    private String status;
    private String species;
    private String type;
    private String gender;
    private Origin origin;
    private Origin location;
    private String image;
    private List<String> episode;

    List<String> getEpisode() {
        if (episode == null) {
            return new ArrayList<>();
        }
        return episode;
    }

}
