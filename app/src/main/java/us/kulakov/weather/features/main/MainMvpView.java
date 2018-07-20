package us.kulakov.weather.features.main;

import us.kulakov.weather.data.application.CurrentWeather;
import us.kulakov.weather.data.application.MultiDayForecast;
import us.kulakov.weather.features.base.MvpView;

public interface MainMvpView extends MvpView {

    void showForecast(MultiDayForecast forecast);

    void showCurrentWeather(CurrentWeather weather);

    void showProgress(boolean show);

    void showError(Throwable error);
}
