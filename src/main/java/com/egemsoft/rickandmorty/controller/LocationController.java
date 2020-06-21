package com.egemsoft.rickandmorty.controller;

import com.egemsoft.rickandmorty.domain.GenericResponse;
import com.egemsoft.rickandmorty.domain.location.Location;
import com.egemsoft.rickandmorty.service.LocationService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping()
    public GenericResponse<Location> getLocationList(@RequestParam Optional<Integer> page,
                                                     @RequestParam Optional<String> orderBy) {
        return locationService.getLocationList(page, orderBy);
    }

    @GetMapping("{id}")
    public Location getLocationWithId(@PathVariable Integer id) {
        return locationService.getLocationWithId(id);
    }

}
