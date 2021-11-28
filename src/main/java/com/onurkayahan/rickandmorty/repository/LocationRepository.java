package com.onurkayahan.rickandmorty.repository;

import com.onurkayahan.rickandmorty.domain.location.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends MongoRepository<Location, Integer> {
}
