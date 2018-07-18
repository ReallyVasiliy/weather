package us.kulakov.weather.data.remote;

import us.kulakov.weather.data.remote.model.response.Pokemon;
import us.kulakov.weather.data.remote.model.response.PokemonListResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OWMService {

    String API_KEY = "4601c92fd081624f71bba9238f9e3a59";

    @GET("pokemon")
    Single<PokemonListResponse> getDailyForecast(@Query("limit") int limit);

    @GET("pokemon/{name}")
    Single<Pokemon> getCurrentWeather(@Path("name") String name);
}
