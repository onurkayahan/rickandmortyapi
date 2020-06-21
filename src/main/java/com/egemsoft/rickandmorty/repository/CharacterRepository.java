package com.egemsoft.rickandmorty.repository;

import com.egemsoft.rickandmorty.domain.character.Character;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends MongoRepository<Character, Integer> {
}
