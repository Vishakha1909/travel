package com.amdocs.travel.Travel.controller;

import com.amdocs.travel.Travel.model.City;
import com.amdocs.travel.Travel.model.Travels;
import com.amdocs.travel.Travel.repository.CityRepository;
import com.amdocs.travel.Travel.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travel")
public class TravelController {
    @Autowired
    private TravelService travelService;
    @Autowired
    private CityRepository cityRepository;

    @GetMapping("/all-paths/{sourceCityId}/{destinationCityId}")
    public List<List<Travels>> getAllPathsBetweenCities(@PathVariable Long sourceCityId, @PathVariable Long destinationCityId) {
        City sourceCity = cityRepository.findById(sourceCityId).orElse(null);
        City destinationCity = cityRepository.findById(destinationCityId).orElse(null);

        if (sourceCity != null && destinationCity != null) {
            return travelService.getAllPathsBetweenCities(sourceCity, destinationCity);
        }
        return null;
    }

    @GetMapping("/fastest-path/{sourceCityId}/{destinationCityId}")
    public List<Travels> getFastestPathBetweenCities(@PathVariable Long sourceCityId, @PathVariable Long destinationCityId) {
        City sourceCity = cityRepository.findById(sourceCityId).orElse(null);
        City destinationCity = cityRepository.findById(destinationCityId).orElse(null);

        if (sourceCity != null && destinationCity != null) {
            return travelService.getFastestPathBetweenCities(sourceCity, destinationCity);
        }
        return null;
    }

    @GetMapping("/cheapest-path/{sourceCityId}/{destinationCityId}")
    public List<Travels> getCheapestPathBetweenCities(@PathVariable Long sourceCityId, @PathVariable Long destinationCityId) {
        City sourceCity = cityRepository.findById(sourceCityId).orElse(null);
        City destinationCity = cityRepository.findById(destinationCityId).orElse(null);

        if (sourceCity != null && destinationCity != null) {
            return travelService.getCheapestPathBetweenCities(sourceCity, destinationCity);
        }
        return null;
    }
}

