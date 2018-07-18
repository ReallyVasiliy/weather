package us.kulakov.weather.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import us.kulakov.weather.data.remote.model.response.Pokemon;
import us.kulakov.weather.data.remote.OWMService;
import io.reactivex.Single;

@Singleton
public class DataManager {

    private OWMService mOWMService;

    @Inject
    public DataManager(OWMService OWMService) {
        this.mOWMService = OWMService;
    }

    public Single<List<String>> getPokemonList(int limit) {
        return mOWMService
                .getDailyForecast(limit)
                .toObservable()
                .flatMapIterable(namedResources -> namedResources.results)
                .map(namedResource -> namedResource.name)
                .toList();
    }

    public Single<Pokemon> getPokemon(String name) {
        return mOWMService.getCurrentWeather(name);
    }
}
