package com.propertysystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class PropertyScheduler {

    @Autowired
    private PropertyService propertyService;

    @Scheduled(fixedDelay = 1000)
    public void updateData() {
        propertyService.updateData();
    }
}
