package us.kulakov.weather.data.remote.entities.forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vasiliy Kulakov on 7/17/18.
 * This file is part of the project "weather".
 * Copyright (c) 2018 Vasiliy Kulakov
 *
 * OWM API model. Generated with help from jsonschema2pojo.org
 */
public class OWMTemp {

    @SerializedName("day")
    @Expose
    private Double day;
    @SerializedName("min")
    @Expose
    private Double min;
    @SerializedName("max")
    @Expose
    private Double max;
    @SerializedName("night")
    @Expose
    private Double night;
    @SerializedName("eve")
    @Expose
    private Double eve;
    @SerializedName("morn")
    @Expose
    private Double morn;

    /**
     * No args constructor for use in serialization
     *
     */
    public OWMTemp() {
    }

    /**
     *
     * @param min
     * @param eve
     * @param max
     * @param morn
     * @param night
     * @param day
     */
    public OWMTemp(Double day, Double min, Double max, Double night, Double eve, Double morn) {
        super();
        this.day = day;
        this.min = min;
        this.max = max;
        this.night = night;
        this.eve = eve;
        this.morn = morn;
    }

    public Double getDay() {
        return day;
    }

    public void setDay(Double day) {
        this.day = day;
    }

    public OWMTemp withDay(Double day) {
        this.day = day;
        return this;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public OWMTemp withMin(Double min) {
        this.min = min;
        return this;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public OWMTemp withMax(Double max) {
        this.max = max;
        return this;
    }

    public Double getNight() {
        return night;
    }

    public void setNight(Double night) {
        this.night = night;
    }

    public OWMTemp withNight(Double night) {
        this.night = night;
        return this;
    }

    public Double getEve() {
        return eve;
    }

    public void setEve(Double eve) {
        this.eve = eve;
    }

    public OWMTemp withEve(Double eve) {
        this.eve = eve;
        return this;
    }

    public Double getMorn() {
        return morn;
    }

    public void setMorn(Double morn) {
        this.morn = morn;
    }

    public OWMTemp withMorn(Double morn) {
        this.morn = morn;
        return this;
    }

}