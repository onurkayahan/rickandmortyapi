package com.egemsoft.rickandmorty.service;

import com.egemsoft.rickandmorty.domain.GenericResponse;
import com.egemsoft.rickandmorty.domain.Info;
import com.egemsoft.rickandmorty.domain.location.Location;
import com.egemsoft.rickandmorty.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final HttpServletRequest httpServletRequest;

    private Logger logger = LoggerFactory.getLogger(getClass());


    public LocationService(LocationRepository locationRepository, HttpServletRequest httpServletRequest) {
        this.locationRepository = locationRepository;
        this.httpServletRequest = httpServletRequest;
    }

    public void saveAll(List<Location> locations) {
        locationRepository.saveAll(locations);
    }


    /**
     * Returns one page of locations with info, also can be sortable
     * @param pageOpt
     * @param orderBy
     * @return
     */
    public GenericResponse<Location> getLocationList(Optional<Integer> pageOpt, Optional<String> orderBy) {
        Pageable pageable = orderBy.map(s -> PageRequest.of(pageOpt.orElse(1), 20, new Sort(Sort.Direction.ASC, s)))
                .orElseGet(() -> PageRequest.of(pageOpt.orElse(1), 20));

        Page<Location> locations = locationRepository.findAll(pageable);

        int page = pageOpt.orElse(1);

        if (locations.getContent().isEmpty()) {
            logger.warn("Invalid page number" + page);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "{\"error\":\"There is nothing here\"}");
        }

        //Fetch request for obtain current URL
        String request = httpServletRequest.getRequestURL().append('?').append(httpServletRequest.getQueryString()).toString();

        Info info = new Info()
                .setNext(page == locations.getTotalPages() ? null : request.replace("page=" + page, "page=" + (page + 1)))
                .setCount(locations.getTotalElements())
                .setPrev(page == 0 || page == 1 ? null : request.replace("page=" + page, "page=" + (page - 1)))
                .setPages(locations.getTotalPages());
        return new GenericResponse<Location>()
                .setInfo(info)
                .setResults(locations.getContent());
    }

    public Location getLocationWithId(Integer id) {
        Optional<Location> locationOptional = locationRepository.findById(id);
        if (locationOptional.isPresent()) {
            return locationOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "{\"error\":\"Location not found\"}");
        }
    }

}
