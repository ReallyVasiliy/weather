package us.kulakov.weather.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import us.kulakov.weather.data.DataManager;
import us.kulakov.weather.injection.ApplicationContext;
import us.kulakov.weather.injection.module.AppModule;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ApplicationContext
    Context context();

    Application application();

    DataManager apiManager();
}
