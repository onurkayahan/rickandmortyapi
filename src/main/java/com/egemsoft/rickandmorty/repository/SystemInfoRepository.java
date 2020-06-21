package com.egemsoft.rickandmorty.repository;

import com.egemsoft.rickandmorty.domain.SystemInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SystemInfoRepository extends MongoRepository<SystemInfo,Integer> {
}
