package us.kulakov.weather.features.main;

import javax.inject.Inject;

import us.kulakov.weather.data.WeatherRepository;
import us.kulakov.weather.data.application.LatLong;
import us.kulakov.weather.features.base.BasePresenter;
import us.kulakov.weather.injection.ConfigPersistent;
import us.kulakov.weather.util.rx.scheduler.SchedulerUtils;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private static final int FORECAST_DAYS_LIMIT = 16;

    private final WeatherRepository dataManager;

    @Inject
    public MainPresenter(WeatherRepository dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    public void fetchWeatherData(LatLong latLong) {
        checkViewAttached();
        getView().showProgress(true);

        addDisposable(dataManager
                .queryMultiDayForecast(latLong, FORECAST_DAYS_LIMIT)
                .compose(SchedulerUtils.ioToMain())
                .subscribe(
                        multiDayForecast -> {
                            getView().showProgress(false);
                            getView().showForecast(multiDayForecast);
                        },
                        throwable -> {
                            getView().showProgress(false);
                            getView().showError(throwable);
                        }));
    }
}
