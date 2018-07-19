package us.kulakov.weather.data.remote.entities.current;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vasiliy Kulakov on 7/17/18.
 * This file is part of the project "weather".
 * Copyright (c) 2018 Vasiliy Kulakov
 *
 * OWM API model. Generated with help from jsonschema2pojo.org
 */
public class OWMSys {

    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("sunrise")
    @Expose
    private Long sunrise;
    @SerializedName("sunset")
    @Expose
    private Long sunset;

    /**
     * No args constructor for use in serialization
     *
     */
    public OWMSys() {
    }

    /**
     *
     * @param message
     * @param sunset
     * @param sunrise
     * @param country
     */
    public OWMSys(Double message, String country, Long sunrise, Long sunset) {
        super();
        this.message = message;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public OWMSys withMessage(Double message) {
        this.message = message;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public OWMSys withCountry(String country) {
        this.country = country;
        return this;
    }

    public Long getSunrise() {
        return sunrise;
    }

    public void setSunrise(Long sunrise) {
        this.sunrise = sunrise;
    }

    public OWMSys withSunrise(Long sunrise) {
        this.sunrise = sunrise;
        return this;
    }

    public Long getSunset() {
        return sunset;
    }

    public void setSunset(Long sunset) {
        this.sunset = sunset;
    }

    public OWMSys withSunset(Long sunset) {
        this.sunset = sunset;
        return this;
    }

}