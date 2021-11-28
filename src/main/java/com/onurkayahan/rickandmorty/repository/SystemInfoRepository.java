package com.onurkayahan.rickandmorty.repository;

import com.onurkayahan.rickandmorty.domain.SystemInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SystemInfoRepository extends MongoRepository<SystemInfo,Integer> {
}
