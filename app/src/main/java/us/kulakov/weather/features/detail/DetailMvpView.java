package us.kulakov.weather.features.detail;

import us.kulakov.weather.data.application.DayForecast;
import us.kulakov.weather.features.base.MvpView;

public interface DetailMvpView extends MvpView {

    void showWeather(DayForecast forecast);

    void showProgress(boolean show);

    void showError(Throwable error);
}
