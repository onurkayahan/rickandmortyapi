package com.egemsoft.rickandmorty.controller;

import com.egemsoft.rickandmorty.domain.SystemInfo;
import com.egemsoft.rickandmorty.service.SystemInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/systemInfo")
public class SystemInfoController {

    private final SystemInfoService systemInfoService;

    public SystemInfoController(SystemInfoService systemInfoService) {
        this.systemInfoService = systemInfoService;
    }

    @GetMapping
    public SystemInfo getSystemInfo() {
        return systemInfoService.getSystemInfo();
    }

}

