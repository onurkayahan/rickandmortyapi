package com.egemsoft.rickandmorty.repository;

import com.egemsoft.rickandmorty.domain.location.Location;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends MongoRepository<Location, Integer> {
}
