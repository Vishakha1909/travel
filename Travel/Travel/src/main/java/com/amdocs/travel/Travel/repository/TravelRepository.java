package com.amdocs.travel.Travel.repository;

import com.amdocs.travel.Travel.model.City;
import com.amdocs.travel.Travel.model.Travels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface TravelRepository extends JpaRepository<Travels, Long> {
    List<Travels> findBySourceCityAndDestinationCity(City sourceCity, City destinationCity);

    List<Travels> findBySourceCity(City sourceCity);
    @Query("SELECT t FROM Travels t WHERE t.sourceCity = :sourceCity AND t.destinationCity = :destinationCity ORDER BY t.timeTaken ASC")
    Travels findFastestTravelBetweenCities(@PathVariable("sourceCity") City sourceCity, @PathVariable("destinationCity") City destinationCity);

    @Query("SELECT t FROM Travels t WHERE t.sourceCity = :sourceCity AND t.destinationCity = :destinationCity ORDER BY t.travelPrice ASC")
    Travels findCheapestTravelBetweenCities(@PathVariable("sourceCity") City sourceCity, @PathVariable("destinationCity") City destinationCity);
}

