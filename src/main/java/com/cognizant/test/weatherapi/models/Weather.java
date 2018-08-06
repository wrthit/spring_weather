package com.cognizant.test.weatherapi.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import com.cognizant.test.weatherapi.models.Location;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@JsonPropertyOrder({"id", "date", "location", "temperature"})
public class Weather{

	@Id
    @Column(name = "weather_id", unique = true)
    private Long id;

    @JsonProperty("date")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate recordedDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="location_id")
    private Location location;

    //up to one decimal place
    @ElementCollection
    private List<Float> temperature;

    public Weather(){
    }

    public Weather (Long id, LocalDate recordedDate, Location location, List<Float> temps){
        this.id = id;
        this.recordedDate = recordedDate;
        this.location = location;
        this.temperature = temps;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public LocalDate getRecordedDate(){
        return recordedDate;
    }

    public void setRecordedDate(LocalDate recordedDate){
        this.recordedDate = recordedDate;
    }

    public Location getLocation(){
        return location;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    public List<Float> getTemperature(){
        return temperature;
    }

    public void setTemperature(List<Float> temps){     
        this.temperature = temps;
    }
}