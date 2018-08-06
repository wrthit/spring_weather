package com.cognizant.test.weatherapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.test.weatherapi.models.Location;

public interface LocationRepository extends JpaRepository<Location, Long>{

}