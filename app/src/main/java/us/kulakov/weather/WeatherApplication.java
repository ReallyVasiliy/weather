package us.kulakov.weather;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.singhajit.sherlock.core.Sherlock;
import com.squareup.leakcanary.LeakCanary;
import com.tspoon.traceur.Traceur;

import timber.log.Timber;
import us.kulakov.weather.injection.component.AppComponent;
import us.kulakov.weather.injection.component.DaggerAppComponent;
import us.kulakov.weather.injection.module.AppModule;
import us.kulakov.weather.injection.module.NetworkModule;

public class WeatherApplication extends MultiDexApplication {

    private AppComponent appComponent;

    public static WeatherApplication get(Context context) {
        return (WeatherApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            LeakCanary.install(this);
            Sherlock.init(this);
            Traceur.enableLogging();
        }
    }

    public AppComponent getComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .networkModule(new NetworkModule(this))
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
    }
}
