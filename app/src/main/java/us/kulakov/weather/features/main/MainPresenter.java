package us.kulakov.weather.features.main;

import javax.inject.Inject;

import us.kulakov.weather.data.DataManager;
import us.kulakov.weather.features.base.BasePresenter;
import us.kulakov.weather.injection.ConfigPersistent;
import us.kulakov.weather.util.rx.scheduler.SchedulerUtils;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private static final int FORECAST_DAYS_LIMIT = 16;

    private final DataManager dataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    public void getPokemon() {
        checkViewAttached();
        getView().showProgress(true);
        dataManager
                .getPokemonList(FORECAST_DAYS_LIMIT)
                .compose(SchedulerUtils.ioToMain())
                .subscribe(
                        pokemons -> {
                            getView().showProgress(false);
                            getView().showPokemon(pokemons);
                        },
                        throwable -> {
                            getView().showProgress(false);
                            getView().showError(throwable);
                        });
    }
}
