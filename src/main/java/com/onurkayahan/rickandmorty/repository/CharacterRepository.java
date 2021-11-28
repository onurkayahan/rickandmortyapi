package com.onurkayahan.rickandmorty.repository;

import com.onurkayahan.rickandmorty.domain.character.Character;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends MongoRepository<Character, Integer> {
}
