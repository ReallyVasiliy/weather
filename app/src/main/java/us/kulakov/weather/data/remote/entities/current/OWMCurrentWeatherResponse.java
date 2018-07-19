package us.kulakov.weather.data.remote.entities.current;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import us.kulakov.weather.data.remote.entities.common.OWMCoord;
import us.kulakov.weather.data.remote.entities.common.OWMWeather;


/**
 * Created by Vasiliy Kulakov on 7/17/18.
 * This file is part of the project "weather".
 * Copyright (c) 2018 Vasiliy Kulakov
 *
 * OWM API model. Generated with help from jsonschema2pojo.org
 */
public class OWMCurrentWeatherResponse {

    @SerializedName("coord")
    @Expose
    private OWMCoord coord;
    @SerializedName("weather")
    @Expose
    private List<OWMWeather> weather = null;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("main")
    @Expose
    private OWMMain main;
    @SerializedName("wind")
    @Expose
    private OWMWind wind;
    @SerializedName("clouds")
    @Expose
    private OWMClouds clouds;
    @SerializedName("dt")
    @Expose
    private Long dt;
    @SerializedName("sys")
    @Expose
    private OWMSys sys;
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cod")
    @Expose
    private Long cod;

    /**
     * No args constructor for use in serialization
     *
     */
    public OWMCurrentWeatherResponse() {
    }

    /**
     *
     * @param id
     * @param dt
     * @param clouds
     * @param coord
     * @param wind
     * @param cod
     * @param sys
     * @param name
     * @param base
     * @param weather
     * @param main
     */
    public OWMCurrentWeatherResponse(OWMCoord coord, List<OWMWeather> weather, String base, OWMMain main, OWMWind wind, OWMClouds clouds, Long dt, OWMSys sys, Long id, String name, Long cod) {
        super();
        this.coord = coord;
        this.weather = weather;
        this.base = base;
        this.main = main;
        this.wind = wind;
        this.clouds = clouds;
        this.dt = dt;
        this.sys = sys;
        this.id = id;
        this.name = name;
        this.cod = cod;
    }

    public OWMCoord getCoord() {
        return coord;
    }

    public void setCoord(OWMCoord coord) {
        this.coord = coord;
    }

    public OWMCurrentWeatherResponse withCoord(OWMCoord coord) {
        this.coord = coord;
        return this;
    }

    public List<OWMWeather> getWeather() {
        return weather;
    }

    public void setWeather(List<OWMWeather> weather) {
        this.weather = weather;
    }

    public OWMCurrentWeatherResponse withWeather(List<OWMWeather> weather) {
        this.weather = weather;
        return this;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public OWMCurrentWeatherResponse withBase(String base) {
        this.base = base;
        return this;
    }

    public OWMMain getMain() {
        return main;
    }

    public void setMain(OWMMain main) {
        this.main = main;
    }

    public OWMCurrentWeatherResponse withMain(OWMMain main) {
        this.main = main;
        return this;
    }

    public OWMWind getWind() {
        return wind;
    }

    public void setWind(OWMWind wind) {
        this.wind = wind;
    }

    public OWMCurrentWeatherResponse withWind(OWMWind wind) {
        this.wind = wind;
        return this;
    }

    public OWMClouds getClouds() {
        return clouds;
    }

    public void setClouds(OWMClouds clouds) {
        this.clouds = clouds;
    }

    public OWMCurrentWeatherResponse withClouds(OWMClouds clouds) {
        this.clouds = clouds;
        return this;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public OWMCurrentWeatherResponse withDt(Long dt) {
        this.dt = dt;
        return this;
    }

    public OWMSys getSys() {
        return sys;
    }

    public void setSys(OWMSys sys) {
        this.sys = sys;
    }

    public OWMCurrentWeatherResponse withSys(OWMSys sys) {
        this.sys = sys;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OWMCurrentWeatherResponse withId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OWMCurrentWeatherResponse withName(String name) {
        this.name = name;
        return this;
    }

    public Long getCod() {
        return cod;
    }

    public void setCod(Long cod) {
        this.cod = cod;
    }

    public OWMCurrentWeatherResponse withCod(Long cod) {
        this.cod = cod;
        return this;
    }

}