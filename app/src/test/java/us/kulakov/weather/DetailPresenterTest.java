package us.kulakov.weather;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import us.kulakov.weather.data.WeatherRepository;
import us.kulakov.weather.data.application.DayForecast;
import us.kulakov.weather.features.detail.DetailMvpView;
import us.kulakov.weather.features.detail.DetailPresenter;
import us.kulakov.weather.util.RxSchedulersOverrideRule;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DetailPresenterTest {

    @Rule
    public final RxSchedulersOverrideRule overrideSchedulersRule = new RxSchedulersOverrideRule();

    @Mock
    DetailMvpView mockDetailMvpView;
    @Mock
    WeatherRepository mockDataManager;

    private DetailPresenter detailPresenter;

    @Before
    public void setUp() {
        detailPresenter = new DetailPresenter(mockDataManager);
        detailPresenter.attachView(mockDetailMvpView);
    }

    @After
    public void tearDown() {
        detailPresenter.detachView();
    }

    @Test
    public void getForecastShowsWeather() {
        String forecastId = "3028381014";
        DayForecast mockForecast = mock(DayForecast.class);
        when(mockDataManager.queryCachedForecast(forecastId))
                .thenReturn(mockForecast);

        detailPresenter.getForecast(forecastId);

        verify(mockDetailMvpView).showWeather(mockForecast);
        verify(mockDetailMvpView, never()).showError(any(Throwable.class));
    }

    @Test
    public void getForeastShowsError() throws Exception {
        String forecastId = "3028381014";
        when(mockDataManager.queryCachedForecast(anyString()))
                .thenReturn(null);

        detailPresenter.getForecast(forecastId);

        verify(mockDetailMvpView).showError(any(Throwable.class));
        verify(mockDetailMvpView, never()).showWeather(any(DayForecast.class));
    }
}
