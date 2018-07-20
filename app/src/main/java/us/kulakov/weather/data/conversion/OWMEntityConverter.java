package us.kulakov.weather.data.conversion;

import android.os.SystemClock;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import us.kulakov.weather.BuildConfig;
import us.kulakov.weather.data.application.City;
import us.kulakov.weather.data.application.CurrentWeather;
import us.kulakov.weather.data.application.CurrentWeatherBuilder;
import us.kulakov.weather.data.application.DayForecast;
import us.kulakov.weather.data.application.DayForecastBuilder;
import us.kulakov.weather.data.application.LatLong;
import us.kulakov.weather.data.application.MultiDayForecast;
import us.kulakov.weather.data.remote.entities.common.OWMCoord;
import us.kulakov.weather.data.remote.entities.common.OWMWeather;
import us.kulakov.weather.data.remote.entities.current.OWMCurrentWeatherResponse;
import us.kulakov.weather.data.remote.entities.current.OWMMain;
import us.kulakov.weather.data.remote.entities.forecast.OWMCity;
import us.kulakov.weather.data.remote.entities.forecast.OWMDailyForecastResponse;
import us.kulakov.weather.data.remote.entities.forecast.OWMDayForecast;
import us.kulakov.weather.data.remote.entities.forecast.OWMTemp;

/**
 * Created by Vasiliy Kulakov on 7/18/18.
 * This file is part of the project "weather".
 * Copyright (c) 2018 Vasiliy Kulakov
 *
 * Converts between API and application domain objects
 * TODO: This class can get out of hand, maybe we decorate our app & API entities w/conversion functionality
 */
public class OWMEntityConverter {

    @Inject
    public OWMEntityConverter() {
    }

    public CurrentWeather convertCurrentWeather(OWMCurrentWeatherResponse response) {

        CurrentWeatherBuilder builder = new CurrentWeatherBuilder();

        // Convert coord to app entity
        OWMCoord coord = response.getCoord();
        if (coord != null && coord.getLat() != null && coord.getLon() != null) {
            LatLong latLong = new LatLong(coord.getLat(), coord.getLon());
            builder.setLatLong(latLong);
        }

        List<OWMWeather> weatherList = response.getWeather();
        if (weatherList != null && weatherList.size() > 0) {
            OWMWeather weather = weatherList.get(0);

            builder.setTitle(weather.getMain());

            if (weather.getIcon() != null) {
                String imageUrl = String.format(BuildConfig.OWM_ICON_URL_FORMAT, weather.getIcon());
                builder.setImageURL(imageUrl);
            }
        }

        OWMMain main = response.getMain();
        if (main != null) {
            builder.setHumidity(main.getHumidity());
            builder.setTemp(main.getTemp());
        }

        builder.setLocationName(response.getName());

        return builder.createCurrentWeather();
    }

    public MultiDayForecast convertDailyForecast(OWMDailyForecastResponse response) {
        OWMCity responseCity = response.getCity();
        City city = new City(responseCity.getName(), responseCity.getCountry());

        List<DayForecast> dayForecastList;
        if (response.getList() != null) {
            dayForecastList = new ArrayList<>(response.getList().size());
            for (OWMDayForecast responseForecast : response.getList()) {
                if (responseForecast.getDt() != null && responseForecast.getDt() * 1000L < System.currentTimeMillis()) {
                    continue;
                }
                dayForecastList.add(convertDayForecast(responseForecast));
            }
        } else {
            dayForecastList = new ArrayList<>(0);
        }

        return new MultiDayForecast(city, dayForecastList);
    }

    private DayForecast convertDayForecast(OWMDayForecast response) {

        DayForecastBuilder builder = new DayForecastBuilder();

        OWMTemp temp = response.getTemp();
        if (temp != null) {
            builder.setTempMax(temp.getMax()).setTempMin(temp.getMin());
        }

        List<OWMWeather> weatherList = response.getWeather();
        if (weatherList != null && weatherList.size() > 0) {
            OWMWeather weather = weatherList.get(0);

            builder.setTitle(weather.getMain());
            builder.setDescription(weather.getDescription());

            if (weather.getIcon() != null) {
                String imageUrl = String.format(BuildConfig.OWM_ICON_URL_FORMAT, weather.getIcon());
                builder.setImageURL(imageUrl);
            }
        }

        if (response.getDt() != null) {
            builder.setTimestamp(response.getDt());
        }

        if (response.getHumidity() != null) {
            builder.setHumidity(response.getHumidity());
        }

        return builder.createDayForecast();
    }
}
