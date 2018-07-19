package us.kulakov.weather.injection.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import us.kulakov.weather.data.remote.OWMService;
import retrofit2.Retrofit;

@Module(includes = {NetworkModule.class})
public class ApiModule {

    @Provides
    @Singleton
    OWMService provideOWMApi(Retrofit retrofit) {
        return retrofit.create(OWMService.class);
    }
}
