package us.kulakov.weather.common.injection.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import us.kulakov.weather.data.WeatherRepository;
import us.kulakov.weather.data.remote.OWMService;
import us.kulakov.weather.injection.ApplicationContext;

import static org.mockito.Mockito.mock;

/**
 * Provides application-level dependencies for an app running on a testing environment This allows
 * injecting mocks if necessary.
 */
@Module
public class ApplicationTestModule {
    private final Application mApplication;

    public ApplicationTestModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    /**
     * ********** MOCKS ***********
     */
    @Provides
    @Singleton
    WeatherRepository providesDataManager() {
        return mock(WeatherRepository.class);
    }

    @Provides
    @Singleton
    OWMService provideMvpBoilerplateService() {
        return mock(OWMService.class);
    }
}
