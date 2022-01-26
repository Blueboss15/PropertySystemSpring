package com.propertysystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertySystemApi {
    @Autowired
    private PropertyService propertyService;

    @GetMapping(path="/api/real-estates-stats/{regionID}")
    public ResponseEntity<Double> getAveragePrice(@PathVariable String regionID, @RequestParam String size, @RequestParam String types, @RequestParam String dateSince, @RequestParam String dateUntil)
    {
        double averageValue = propertyService.getAverageValue(regionID, size, types, dateSince, dateUntil);
        return new ResponseEntity<>(averageValue, HttpStatus.OK);
    }
}
