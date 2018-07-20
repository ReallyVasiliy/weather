package us.kulakov.weather;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import us.kulakov.weather.common.TestDataFactory;
import us.kulakov.weather.data.WeatherRepository;
import us.kulakov.weather.data.application.CurrentWeather;
import us.kulakov.weather.data.application.LatLong;
import us.kulakov.weather.data.application.MultiDayForecast;
import us.kulakov.weather.data.conversion.OWMEntityConverter;
import us.kulakov.weather.data.remote.OWMService;
import us.kulakov.weather.data.remote.entities.current.OWMCurrentWeatherResponse;
import us.kulakov.weather.data.remote.entities.forecast.OWMDailyForecastResponse;
import us.kulakov.weather.util.RxSchedulersOverrideRule;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherRepositoryTest {

    @Rule
    public final RxSchedulersOverrideRule overrideSchedulersRule = new RxSchedulersOverrideRule();

    @Mock
    private OWMService owmService;

    private OWMEntityConverter converter;

    private WeatherRepository dataManager;

    @Before
    public void setUp() {
        converter = new OWMEntityConverter();

        dataManager = new WeatherRepository(owmService, converter);
    }

    @Test
    public void getForecastCompletesAndEmitsForecast() {
        OWMDailyForecastResponse response = TestDataFactory.createApiForecastResponse(16);

        MultiDayForecast expectedForecast = converter.convertDailyForecast(response);

        when(owmService.getDailyForecast(anyString(), anyString(), anyString(), anyInt()))
                .thenReturn(Observable.just(response));

        dataManager
                .queryMultiDayForecast(new LatLong(-25.0, 50.0), 16)
                .test()
                .assertComplete()
                .assertValue(multiDayForecast ->
                        expectedForecast.city.city.equals(multiDayForecast.city.city) &&
                                expectedForecast.city.country.equals(multiDayForecast.city.country))
                .assertValue(multiDayForecast ->
                        expectedForecast.forecastList.size() == multiDayForecast.forecastList.size());
    }

    @Test
    public void getCurrentWeatherCompletesAndEmitsWeather() {
        OWMCurrentWeatherResponse currentWeatherResponse = TestDataFactory.createCurrentWeatherResponse();

        CurrentWeather expectedWeather = converter.convertCurrentWeather(currentWeatherResponse);

        when(owmService.getCurrentWeather(anyString(), anyString(), anyString()))
                .thenReturn(Observable.just(currentWeatherResponse));

        dataManager.queryCurrentWeather(new LatLong(-25, 50))
                .test()
                .assertComplete()
                .assertValue(currentWeather -> expectedWeather.title.equals(currentWeather.title));
    }


    @Test
    public void getCurrentWeatherQueriesApi() {
        OWMCurrentWeatherResponse currentWeatherResponse = TestDataFactory.createCurrentWeatherResponse();

        when(owmService.getCurrentWeather(anyString(), anyString(), anyString()))
                .thenReturn(Observable.just(currentWeatherResponse));

        dataManager.queryCurrentWeather(new LatLong(-52.3, 80.2))
                .test()
                .assertComplete();

        verify(owmService).getCurrentWeather(anyString(), eq("-52"), eq("80"));
    }


}
