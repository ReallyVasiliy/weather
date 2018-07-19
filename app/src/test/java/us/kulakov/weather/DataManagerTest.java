package us.kulakov.weather;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import us.kulakov.weather.common.TestDataFactory;
import us.kulakov.weather.data.WeatherRepository;
import us.kulakov.weather.data.remote.entities.response.NamedResource;
import us.kulakov.weather.data.remote.entities.response.Pokemon;
import us.kulakov.weather.data.remote.entities.response.PokemonListResponse;
import us.kulakov.weather.data.remote.OWMService;
import us.kulakov.weather.util.RxSchedulersOverrideRule;
import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {

    @Rule
    public final RxSchedulersOverrideRule overrideSchedulersRule = new RxSchedulersOverrideRule();

    @Mock
    private OWMService mMockOWMService;

    private WeatherRepository dataManager;

    @Before
    public void setUp() {
        dataManager = new WeatherRepository(mMockOWMService);
    }

    @Test
    public void getPokemonListCompletesAndEmitsPokemonList() {
        List<NamedResource> namedResourceList = TestDataFactory.makeNamedResourceList(5);
        PokemonListResponse pokemonListResponse = new PokemonListResponse();
        pokemonListResponse.results = namedResourceList;

        when(mMockOWMService.getDailyForecast(anyInt()))
                .thenReturn(Single.just(pokemonListResponse));

        dataManager
                .queryMultiDayForecast(10)
                .test()
                .assertComplete()
                .assertValue(TestDataFactory.makePokemonNameList(namedResourceList));
    }

    @Test
    public void getPokemonCompletesAndEmitsPokemon() {
        String name = "charmander";
        Pokemon pokemon = TestDataFactory.makePokemon(name);
        when(mMockOWMService.getCurrentWeather(anyString())).thenReturn(Single.just(pokemon));

        dataManager.queryCurrentWeather(name).test().assertComplete().assertValue(pokemon);
    }
}
