package us.kulakov.weather.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import us.kulakov.weather.data.application.CurrentWeather;
import us.kulakov.weather.data.application.CurrentWeatherBuilder;
import us.kulakov.weather.data.remote.entities.common.OWMCoord;
import us.kulakov.weather.data.remote.entities.common.OWMWeather;
import us.kulakov.weather.data.remote.entities.current.OWMCurrentWeatherResponse;
import us.kulakov.weather.data.remote.entities.current.OWMMain;
import us.kulakov.weather.data.remote.entities.forecast.OWMCity;
import us.kulakov.weather.data.remote.entities.forecast.OWMDailyForecastResponse;
import us.kulakov.weather.data.remote.entities.forecast.OWMDayForecast;
import us.kulakov.weather.data.remote.entities.forecast.OWMTemp;

/**
 * Factory class that makes instances of data models with random field values. The aim of this class
 * is to help setting up test fixtures.
 */
public class TestDataFactory {

    private static final Random random = new Random();
    private static final long DAY_IN_SECONDS = 60L * 60L * 24L;

    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

    public static OWMDailyForecastResponse createApiForecastResponse(int dayCount) {
        OWMCity city = createCity();
        List<OWMDayForecast> list = createForecastList(dayCount);
        return new OWMDailyForecastResponse()
                .withCity(city)
                .withList(list);
    }

    public static OWMCurrentWeatherResponse createCurrentWeatherResponse() {
        return new OWMCurrentWeatherResponse()
                .withDt(System.currentTimeMillis() / 1000)
                .withWeather(createWeatherList())
                .withMain(new OWMMain().withTemp(50.0).withTempMax(100.0).withTempMin(-100.0).withHumidity(40.0))
                .withName("Edinburgh")
                .withCoord(new OWMCoord().withLat(80.0).withLon(-40.0));
    }

    private static List<OWMDayForecast> createForecastList(int count) {
        List<OWMDayForecast> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(createForecast(i));
        }
        return list;
    }

    private static OWMDayForecast createForecast(int forecastDayIndex) {
        return new OWMDayForecast()
                .withDt(System.currentTimeMillis() / 1000L + forecastDayIndex * DAY_IN_SECONDS)
                .withTemp(new OWMTemp().withMin(-100.0).withMax(100.0).withDay(50.0).withNight(-50.0))
                .withWeather(createWeatherList())
                .withHumidity(random.nextDouble());
    }

    private static List<OWMWeather> createWeatherList() {
        List<OWMWeather> list =  new ArrayList<>(1);
        list.add(createWeather());
        return list;
    }

    private static OWMWeather createWeather() {
        return new OWMWeather()
                .withIcon("icon1")
                .withDescription("Test description")
                .withMain("Test main title")
                .withId(random.nextLong());
    }

    private static OWMCity createCity() {
        return new OWMCity()
                .withCoord(new OWMCoord(100.0, 50.0))
                .withCountry("Northumbria")
                .withName("York")
                .withId(555L);
    }
}
