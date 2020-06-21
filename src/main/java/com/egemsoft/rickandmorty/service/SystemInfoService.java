package com.egemsoft.rickandmorty.service;

import com.egemsoft.rickandmorty.domain.SystemInfo;
import com.egemsoft.rickandmorty.repository.SystemInfoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SystemInfoService {

    private final SystemInfoRepository systemInfoRepository;

    public SystemInfoService(SystemInfoRepository systemInfoRepository) {
        this.systemInfoRepository = systemInfoRepository;
    }

    public SystemInfo getSystemInfo() {
        Optional<SystemInfo> systemInfo = systemInfoRepository.findById(1);
        return systemInfo.get();
    }
}
