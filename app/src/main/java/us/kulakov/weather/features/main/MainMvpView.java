package us.kulakov.weather.features.main;

import java.util.List;

import us.kulakov.weather.features.base.MvpView;

public interface MainMvpView extends MvpView {

    void showPokemon(List<String> pokemon);

    void showProgress(boolean show);

    void showError(Throwable error);
}
