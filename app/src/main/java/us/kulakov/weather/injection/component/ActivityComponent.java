package us.kulakov.weather.injection.component;

import dagger.Subcomponent;
import us.kulakov.weather.features.detail.DetailActivity;
import us.kulakov.weather.features.main.MainActivity;
import us.kulakov.weather.injection.PerActivity;
import us.kulakov.weather.injection.module.ActivityModule;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(DetailActivity detailActivity);
}
