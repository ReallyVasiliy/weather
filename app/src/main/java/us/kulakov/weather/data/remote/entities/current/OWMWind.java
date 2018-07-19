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
public class OWMWind {

    @SerializedName("speed")
    @Expose
    private Double speed;
    @SerializedName("deg")
    @Expose
    private Long deg;

    /**
     * No args constructor for use in serialization
     *
     */
    public OWMWind() {
    }

    /**
     *
     * @param speed
     * @param deg
     */
    public OWMWind(Double speed, Long deg) {
        super();
        this.speed = speed;
        this.deg = deg;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public OWMWind withSpeed(Double speed) {
        this.speed = speed;
        return this;
    }

    public Long getDeg() {
        return deg;
    }

    public void setDeg(Long deg) {
        this.deg = deg;
    }

    public OWMWind withDeg(Long deg) {
        this.deg = deg;
        return this;
    }

}