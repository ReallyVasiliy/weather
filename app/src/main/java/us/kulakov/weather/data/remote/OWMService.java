package us.kulakov.weather.data.remote;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import us.kulakov.weather.data.remote.entities.current.OWMCurrentWeatherResponse;
import us.kulakov.weather.data.remote.entities.forecast.OWMDailyForecastResponse;

public interface OWMService {

    // TODO: Instead of String, lat/lon should be an object, transport layer should deal with converting
    @GET("forecast/daily")
    Observable<OWMDailyForecastResponse> getDailyForecast(@Query("appid") String appId,
                                                          @Query("lat") String lat,
                                                          @Query("lon") String lon,
                                                          @Query("cnt") int numberOfDays);

    @GET("weather")
    Observable<OWMCurrentWeatherResponse> getCurrentWeather(@Query("appid") String appId,
                                                        @Query("lat") String lat,
                                                        @Query("lon") String lon);
}
