package com.amdocs.travel.Travel.repository;

import com.amdocs.travel.Travel.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    // You can add custom query methods here if needed
}
