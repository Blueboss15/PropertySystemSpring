package com.propertysystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PropertyScheduler {

    @Autowired
    private PropertyService propertyService;

    @Scheduled(cron = "0 0 21 * * *")
    public void updateData() {
        propertyService.updateData();
    }
}
