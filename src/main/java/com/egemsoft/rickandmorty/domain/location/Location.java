package com.egemsoft.rickandmorty.domain.location;

import com.egemsoft.rickandmorty.domain.Base;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "Location")
@Accessors(chain = true)
@Getter
@Setter
public class Location extends Base {


    private String type;
    private String dimension;
    private List<String> residents;

    public List<String> getResidents() {
        if (residents == null)
            return new ArrayList<>();
        return residents;
    }

}
