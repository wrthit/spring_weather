package com.cognizant.test.weatherapi.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.cognizant.test.weatherapi.models.Weather;
import com.cognizant.test.weatherapi.repositories.WeatherRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


@RestController
public class WeatherRestController{

    @Autowired
    WeatherRepository weatherRepo;

    //get Weather at certain location (latitude,longitude) order by id
    //return 404 if none exist
    @RequestMapping(value="/weather", method = RequestMethod.GET, params={"lat", "lon"}, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Weather>> getWeatherByLocation(@RequestParam("lat") Float latitude, @RequestParam("lon") Float longitude){

        List<Weather> resultSet = weatherRepo.getWeatherByLocation(latitude, longitude);

        ResponseEntity<List<Weather>> response;

        if(resultSet.size() == 0){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
            response = new ResponseEntity<>(resultSet, HttpStatus.OK);

        return response;
    }

    //get Weather on date sort ascending by id
    //return 404 if none exist
    @RequestMapping(value="/weather", method = RequestMethod.GET, params={"date"}, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Weather>> getWeatherByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
        
        List<Weather> resultSet = weatherRepo.getWeatherByDate(date);

        ResponseEntity<List<Weather>> response;

        if(resultSet.size() == 0){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
            response = new ResponseEntity<>(resultSet, HttpStatus.OK);

        return response;
    }

    //get all weather
    @GetMapping(value="/weather", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Weather>> getAllWeather(){
        ResponseEntity<List<Weather>> response = new ResponseEntity<>(weatherRepo.findAll(), HttpStatus.OK);
        return response;
    }

    //delete all weather recorded between startDate and endDate in a location defined by lat/long
    @RequestMapping(value="/erase", method=RequestMethod.DELETE, params={"startDate", "endDate", "lat", "lon"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteSomeWeather(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate endDate,
            @RequestParam("lat") Float latitude, @RequestParam("lon") Float longitude){
                       
        List<Weather> resultSet = weatherRepo.getWeatherByParams(startDate, endDate, latitude, longitude);
        weatherRepo.deleteInBatch(resultSet);
    }

    //delete all weather
    @DeleteMapping(value="/erase")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllWeather(){
        weatherRepo.deleteAll();
    }

    //post Weather
    //return 400 if Weather exist for that id
    @RequestMapping(value="/weather", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> addWeather(@RequestBody Weather weather){

        Optional<Weather> result = weatherRepo.findById(weather.getId());
 
        if(result.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Weather weatherSaved = weatherRepo.save(weather);
        
        return new ResponseEntity<Weather>(weatherSaved, HttpStatus.CREATED);
    }

}