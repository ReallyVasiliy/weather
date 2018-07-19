package us.kulakov.weather.data.application;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collections;
import java.util.List;

import javax.annotation.concurrent.Immutable;

/**
 * Created by Vasiliy Kulakov on 7/18/18.
 * This file is part of the project "weather".
 * Copyright (c) 2018 Vasiliy Kulakov
 */
public class MultiDayForecast {

    @NonNull
    public final City city;
    public final List<DayForecast> forecastList;

    public MultiDayForecast(@NonNull City city, List<DayForecast> forecastList) {
        this.city = city;
        this.forecastList = Collections.unmodifiableList(forecastList);
    }
}
