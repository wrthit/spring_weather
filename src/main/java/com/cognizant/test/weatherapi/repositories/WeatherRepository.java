package com.cognizant.test.weatherapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

import com.cognizant.test.weatherapi.models.Weather;

public interface WeatherRepository extends JpaRepository<Weather, Long>{

    @Query("SELECT weather from Weather weather WHERE weather.recordedDate = :date order by weather.id")
    List<Weather> getWeatherByDate(LocalDate date);

    @Query("SELECT weather From Weather weather join weather.location loc where loc.latitude = :latitude and loc.longitude = :longitude order by weather.id")
    List<Weather> getWeatherByLocation(Float latitude, Float longitude);

    @Query("Select weather From Weather weather join weather.location loc on loc.latitude = :latitude and loc.longitude = :longitude where weather.recordedDate between :startDate and :endDate order by weather.id")
    List<Weather> getWeatherByParams(LocalDate startDate, LocalDate endDate, Float latitude, Float longitude);
    
    @Override
    @Query("Select weather from Weather weather order by id")
    List<Weather> findAll();

}