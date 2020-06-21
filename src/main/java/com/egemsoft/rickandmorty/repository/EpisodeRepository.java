package com.egemsoft.rickandmorty.repository;

import com.egemsoft.rickandmorty.domain.episode.Episode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeRepository extends MongoRepository<Episode, Integer> {
}
