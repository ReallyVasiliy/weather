package us.kulakov.weather.data.application;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * Created by Vasiliy Kulakov on 7/18/18.
 * This file is part of the project "weather".
 * Copyright (c) 2018 Vasiliy Kulakov
 */
public class City {
    @Nullable
    public final String city;
    @Nullable
    public final String country;

    public City(@Nullable String city, @Nullable String country) {
        this.city = city;
        this.country = country;
    }

    @Nullable
    public String getLocaionName() {
        String location;
        if (city == null && country == null) {
            location = null;
        } else if (city == null) {
            location = country;
        } else if (country == null) {
            location = city;
        } else {
            location = String.format("%s, %s", city, country);
        }
        return location;
    }
}
