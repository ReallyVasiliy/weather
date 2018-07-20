package us.kulakov.weather;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import us.kulakov.weather.data.WeatherRepository;
import us.kulakov.weather.data.application.LatLong;
import us.kulakov.weather.data.application.MultiDayForecast;
import us.kulakov.weather.features.main.MainMvpView;
import us.kulakov.weather.features.main.MainPresenter;
import us.kulakov.weather.util.RxSchedulersOverrideRule;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Rule
    public final RxSchedulersOverrideRule overrideSchedulersRule = new RxSchedulersOverrideRule();

    @Mock
    public MainMvpView mockMainMvpView;
    @Mock
    public WeatherRepository mockDataManager;

    private MainPresenter mainPresenter;

    @Before
    public void setUp() {
        mainPresenter = new MainPresenter(mockDataManager);
        mainPresenter.attachView(mockMainMvpView);
    }

    @After
    public void tearDown() {
        mainPresenter.detachView();
    }

    @Test
    public void getForecastFetchesAndShowsForecast() {
        MultiDayForecast mockMultiDayForecast = mock(MultiDayForecast.class);
        LatLong location = new LatLong(111, 1110);
        when(mockDataManager.queryMultiDayForecast(any(LatLong.class), anyInt()))
                .thenReturn(Observable.just(mockMultiDayForecast));

        mainPresenter.fetchForecast(location);

        verify(mockDataManager).queryMultiDayForecast(eq(location), anyInt());
        verify(mockMainMvpView, times(2)).showProgress(anyBoolean());
        verify(mockMainMvpView).showForecast(mockMultiDayForecast);
        verify(mockMainMvpView, never()).showError(any(Throwable.class));
    }

    @Test
    public void getForecastReturnsError() {
        LatLong location = new LatLong(111, 1110);

        when(mockDataManager.queryMultiDayForecast(any(LatLong.class), anyInt()))
                .thenReturn(Observable.error(new RuntimeException("expected")));

        mainPresenter.fetchForecast(location);

        verify(mockMainMvpView, times(2)).showProgress(anyBoolean());
        verify(mockMainMvpView).showError(any(Throwable.class));
        verify(mockMainMvpView, never()).showForecast(any(MultiDayForecast.class));
    }
}
