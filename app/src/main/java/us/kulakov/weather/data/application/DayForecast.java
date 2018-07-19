package us.kulakov.weather.data.application;

import java.util.UUID;

/**
 * Created by Vasiliy Kulakov on 7/17/18.
 * This file is part of the project "weather".
 * Copyright (c) 2018 Vasiliy Kulakov
 */
public class DayForecast {

    public final String title;
    public final String description;
    public final String imageURL;
    public final Double tempMin;
    public final Double tempMax;
    public final Double humidity;
    public final Long timestamp;

    public final String forecastId;

    DayForecast(String title, String description, String imageURL, Double tempMin, Double tempMax, Double humidity, Long timestamp) {
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.humidity = humidity;
        this.timestamp = timestamp;

        // Needed for caching
        this.forecastId = UUID.randomUUID().toString();
    }
}
