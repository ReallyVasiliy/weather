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
public class OWMMain {

    @SerializedName("temp")
    @Expose
    private Double temp;
    @SerializedName("pressure")
    @Expose
    private Double pressure;
    @SerializedName("humidity")
    @Expose
    private Double humidity;
    @SerializedName("temp_min")
    @Expose
    private Double tempMin;
    @SerializedName("temp_max")
    @Expose
    private Double tempMax;
    @SerializedName("sea_level")
    @Expose
    private Double seaLevel;
    @SerializedName("grnd_level")
    @Expose
    private Double grndLevel;

    /**
     * No args constructor for use in serialization
     *
     */
    public OWMMain() {
    }

    /**
     *
     * @param seaLevel
     * @param humidity
     * @param pressure
     * @param grndLevel
     * @param tempMax
     * @param temp
     * @param tempMin
     */
    public OWMMain(Double temp, Double pressure, Double humidity, Double tempMin, Double tempMax, Double seaLevel, Double grndLevel) {
        super();
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.seaLevel = seaLevel;
        this.grndLevel = grndLevel;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public OWMMain withTemp(Double temp) {
        this.temp = temp;
        return this;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public OWMMain withPressure(Double pressure) {
        this.pressure = pressure;
        return this;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public OWMMain withHumidity(Double humidity) {
        this.humidity = humidity;
        return this;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public OWMMain withTempMin(Double tempMin) {
        this.tempMin = tempMin;
        return this;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public OWMMain withTempMax(Double tempMax) {
        this.tempMax = tempMax;
        return this;
    }

    public Double getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(Double seaLevel) {
        this.seaLevel = seaLevel;
    }

    public OWMMain withSeaLevel(Double seaLevel) {
        this.seaLevel = seaLevel;
        return this;
    }

    public Double getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(Double grndLevel) {
        this.grndLevel = grndLevel;
    }

    public OWMMain withGrndLevel(Double grndLevel) {
        this.grndLevel = grndLevel;
        return this;
    }

}