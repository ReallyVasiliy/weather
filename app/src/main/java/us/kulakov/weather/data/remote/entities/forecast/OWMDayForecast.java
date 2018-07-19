package us.kulakov.weather.data.remote.entities.forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import us.kulakov.weather.data.remote.entities.common.OWMWeather;

/**
 * Created by Vasiliy Kulakov on 7/17/18.
 * This file is part of the project "weather".
 * Copyright (c) 2018 Vasiliy Kulakov
 *
 * OWM API model. Generated with help from jsonschema2pojo.org
 */
public class OWMDayForecast {

    @SerializedName("dt")
    @Expose
    private Long dt;
    @SerializedName("temp")
    @Expose
    private OWMTemp temp;
    @SerializedName("pressure")
    @Expose
    private Double pressure;
    @SerializedName("humidity")
    @Expose
    private Double humidity;
    @SerializedName("weather")
    @Expose
    private java.util.List<OWMWeather> weather = null;
    @SerializedName("speed")
    @Expose
    private Double speed;
    @SerializedName("deg")
    @Expose
    private Long deg;
    @SerializedName("clouds")
    @Expose
    private Long clouds;
    @SerializedName("rain")
    @Expose
    private Double rain;

    /**
     * No args constructor for use in serialization
     *
     */
    public OWMDayForecast() {
    }

    /**
     *
     * @param clouds
     * @param dt
     * @param humidity
     * @param pressure
     * @param speed
     * @param deg
     * @param weather
     * @param temp
     * @param rain
     */
    public OWMDayForecast(Long dt, OWMTemp temp, Double pressure, Double humidity, java.util.List<OWMWeather> weather, Double speed, Long deg, Long clouds, Double rain) {
        super();
        this.dt = dt;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.weather = weather;
        this.speed = speed;
        this.deg = deg;
        this.clouds = clouds;
        this.rain = rain;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public OWMDayForecast withDt(Long dt) {
        this.dt = dt;
        return this;
    }

    public OWMTemp getTemp() {
        return temp;
    }

    public void setTemp(OWMTemp temp) {
        this.temp = temp;
    }

    public OWMDayForecast withTemp(OWMTemp temp) {
        this.temp = temp;
        return this;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public OWMDayForecast withPressure(Double pressure) {
        this.pressure = pressure;
        return this;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public OWMDayForecast withHumidity(Double humidity) {
        this.humidity = humidity;
        return this;
    }

    public java.util.List<OWMWeather> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<OWMWeather> weather) {
        this.weather = weather;
    }

    public OWMDayForecast withWeather(java.util.List<OWMWeather> weather) {
        this.weather = weather;
        return this;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public OWMDayForecast withSpeed(Double speed) {
        this.speed = speed;
        return this;
    }

    public Long getDeg() {
        return deg;
    }

    public void setDeg(Long deg) {
        this.deg = deg;
    }

    public OWMDayForecast withDeg(Long deg) {
        this.deg = deg;
        return this;
    }

    public Long getClouds() {
        return clouds;
    }

    public void setClouds(Long clouds) {
        this.clouds = clouds;
    }

    public OWMDayForecast withClouds(Long clouds) {
        this.clouds = clouds;
        return this;
    }

    public Double getRain() {
        return rain;
    }

    public void setRain(Double rain) {
        this.rain = rain;
    }

    public OWMDayForecast withRain(Double rain) {
        this.rain = rain;
        return this;
    }

}