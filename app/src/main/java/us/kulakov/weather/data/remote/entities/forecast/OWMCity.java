package us.kulakov.weather.data.remote.entities.forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import us.kulakov.weather.data.remote.entities.common.OWMCoord;


/**
 * Created by Vasiliy Kulakov on 7/17/18.
 * This file is part of the project "weather".
 * Copyright (c) 2018 Vasiliy Kulakov
 *
 * OWM API model. Generated with help from jsonschema2pojo.org
 */
public class OWMCity {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("coord")
    @Expose
    private OWMCoord coord;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("population")
    @Expose
    private Long population;

    /**
     * No args constructor for use in serialization
     *
     */
    public OWMCity() {
    }

    /**
     *
     * @param coord
     * @param id
     * @param name
     * @param population
     * @param country
     */
    public OWMCity(Long id, String name, OWMCoord coord, String country, Long population) {
        super();
        this.id = id;
        this.name = name;
        this.coord = coord;
        this.country = country;
        this.population = population;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OWMCity withId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OWMCity withName(String name) {
        this.name = name;
        return this;
    }

    public OWMCoord getCoord() {
        return coord;
    }

    public void setCoord(OWMCoord coord) {
        this.coord = coord;
    }

    public OWMCity withCoord(OWMCoord coord) {
        this.coord = coord;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public OWMCity withCountry(String country) {
        this.country = country;
        return this;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public OWMCity withPopulation(Long population) {
        this.population = population;
        return this;
    }

}