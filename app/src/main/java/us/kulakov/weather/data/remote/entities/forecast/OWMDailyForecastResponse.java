package us.kulakov.weather.data.remote.entities.forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vasiliy Kulakov on 7/17/18.
 * This file is part of the project "weather".
 * Copyright (c) 2018 Vasiliy Kulakov
 *
 * OWM API model. Generated with help from jsonschema2pojo.org
 */
public class OWMDailyForecastResponse {

    @SerializedName("city")
    @Expose
    private OWMCity city;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("cnt")
    @Expose
    private Long cnt;
    @SerializedName("list")
    @Expose
    private List<OWMDayForecast> list = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public OWMDailyForecastResponse() {
    }

    /**
     *
     * @param message
     * @param cnt
     * @param cod
     * @param list
     * @param city
     */
    public OWMDailyForecastResponse(OWMCity city, String cod, Double message, Long cnt, List<OWMDayForecast> list) {
        super();
        this.city = city;
        this.cod = cod;
        this.message = message;
        this.cnt = cnt;
        this.list = list;
    }

    public OWMCity getCity() {
        return city;
    }

    public void setCity(OWMCity city) {
        this.city = city;
    }

    public OWMDailyForecastResponse withCity(OWMCity city) {
        this.city = city;
        return this;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public OWMDailyForecastResponse withCod(String cod) {
        this.cod = cod;
        return this;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public OWMDailyForecastResponse withMessage(Double message) {
        this.message = message;
        return this;
    }

    public Long getCnt() {
        return cnt;
    }

    public void setCnt(Long cnt) {
        this.cnt = cnt;
    }

    public OWMDailyForecastResponse withCnt(Long cnt) {
        this.cnt = cnt;
        return this;
    }

    public List<OWMDayForecast> getList() {
        return list;
    }

    public void setList(List<OWMDayForecast> list) {
        this.list = list;
    }

    public OWMDailyForecastResponse withList(List<OWMDayForecast> list) {
        this.list = list;
        return this;
    }

}
