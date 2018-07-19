package us.kulakov.weather.data;

import android.support.annotation.Nullable;
import android.util.LruCache;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import us.kulakov.weather.BuildConfig;
import us.kulakov.weather.data.application.CurrentWeather;
import us.kulakov.weather.data.application.DayForecast;
import us.kulakov.weather.data.application.LatLong;
import us.kulakov.weather.data.application.MultiDayForecast;
import us.kulakov.weather.data.conversion.OWMEntityConverter;
import us.kulakov.weather.data.remote.OWMService;

/**
 * Created by Vasiliy Kulakov on 7/17/18.
 * This file is part of the project "weather".
 * Copyright (c) 2018 Vasiliy Kulakov
 *
 * This class acts as a repository, with domain-specific use cases. It provides access to entities
 * in various stores.
 * TODO: Refactor into repository, use cases, and other responsibilities
 */
@Singleton
public class WeatherRepository {

    private OWMService owmService;
    private OWMEntityConverter converter;

    private LruCache<String, DayForecast> forecastCache = new LruCache<>(50);


    @Inject
    public WeatherRepository(OWMService owmService, OWMEntityConverter converter) {
        this.owmService = owmService;
        this.converter = converter;
    }

    private static String formatTransportCoordinate(double coordinate) {
        return String.format(Locale.US, "%.0f", coordinate);
    }

    @Nullable
    public DayForecast queryCachedForecast(String forecastId) {
        return forecastCache.get(forecastId);
    }

    public Observable<MultiDayForecast> queryMultiDayForecast(LatLong latLong, int limit) {

        return owmService
                .getDailyForecast(BuildConfig.OWM_API_KEY,
                        formatTransportCoordinate(latLong.latitude),
                        formatTransportCoordinate(latLong.longitude),
                        limit)
                .map(converter::convertDailyForecast)
                .doOnNext(multiDayForecast -> {
                    for (DayForecast dayForecast : multiDayForecast.forecastList) {
                        forecastCache.put(dayForecast.forecastId, dayForecast);
                    }
                });
    }

    public Observable<CurrentWeather> queryCurrentWeather(LatLong latLong) {
        return owmService.getCurrentWeather(BuildConfig.OWM_API_KEY,
                formatTransportCoordinate(latLong.latitude),
                formatTransportCoordinate(latLong.longitude))
                .map(converter::convertCurrentWeather);
    }
}
