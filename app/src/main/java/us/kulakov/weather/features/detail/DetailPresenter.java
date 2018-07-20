package us.kulakov.weather.features.detail;

import javax.inject.Inject;

import us.kulakov.weather.data.WeatherRepository;
import us.kulakov.weather.data.application.DayForecast;
import us.kulakov.weather.features.base.BasePresenter;
import us.kulakov.weather.injection.ConfigPersistent;

@ConfigPersistent
public class DetailPresenter extends BasePresenter<DetailMvpView> {

    private final WeatherRepository dataManager;

    @Inject
    public DetailPresenter(WeatherRepository dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(DetailMvpView mvpView) {
        super.attachView(mvpView);
    }

    public void getForecast(String forecastId) {
        checkViewAttached();
        DayForecast forecast = dataManager.queryCachedForecast(forecastId);
        if (forecast != null) {
            getView().showWeather(forecast);
        } else {
            getView().showError(
                    new Exception(String.format("Forecast %s is no longer in cache", forecastId)));
        }
    }
}
