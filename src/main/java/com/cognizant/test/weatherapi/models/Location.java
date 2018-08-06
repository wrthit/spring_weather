package com.cognizant.test.weatherapi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@JsonPropertyOrder({"lat", "lon", "city", "state"})
public class Location{

    @Id
    @GeneratedValue
    @JsonIgnore
    @Column(name = "location_id")
    private Long id;

    //up to four decimal places
    @JsonProperty("lat")
    private Float latitude;
    
    //up to four decimal places
    @JsonProperty("lon")
    private Float longitude;
    
    private String city;

    private String state;

    public Location(){
    }

    public Location(String city, String state, Float latitude, Float longitude){
        this.city = city;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCity(){
        return city;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getState(){
        return state;
    }

    public void setState(String state){
        this.state = state;
    }

    public Float getLatitude(){
        return latitude;
    }

    public void setLatitude(Float latitude){
        this.latitude = latitude;
    }

    public Float getLongitude(){
        return longitude;
    }

    public void setLongitude(Float longitude){
        this.longitude = longitude;
    }

}