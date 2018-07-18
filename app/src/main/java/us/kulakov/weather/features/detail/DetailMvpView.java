package us.kulakov.weather.features.detail;

import us.kulakov.weather.data.remote.model.response.Pokemon;
import us.kulakov.weather.data.remote.model.response.Statistic;
import us.kulakov.weather.features.base.MvpView;

public interface DetailMvpView extends MvpView {

    void showPokemon(Pokemon pokemon);

    void showStat(Statistic statistic);

    void showProgress(boolean show);

    void showError(Throwable error);
}
