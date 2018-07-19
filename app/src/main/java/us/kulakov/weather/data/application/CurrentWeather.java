package us.kulakov.weather.data.application;

/**
 * Created by Vasiliy Kulakov on 7/18/18.
 * This file is part of the project "weather".
 * Copyright (c) 2018 Vasiliy Kulakov
 */
public class CurrentWeather {

    public final String title;
    public final LatLong latLong;
    public final Double temp;
    public final Double humidity;
    public final String locationName;
    public final String imageURL;


    public CurrentWeather(String title, LatLong latLong, Double temp, Double humidity, String locationName, String imageURL) {
        this.title = title;
        this.latLong = latLong;
        this.temp = temp;
        this.humidity = humidity;
        this.locationName = locationName;
        this.imageURL = imageURL;
    }
}
