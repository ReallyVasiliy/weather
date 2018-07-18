package us.kulakov.weather.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import us.kulakov.weather.common.injection.module.ApplicationTestModule;
import us.kulakov.weather.injection.component.AppComponent;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends AppComponent {
}
