package us.kulakov.weather.common;

import android.content.Context;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import us.kulakov.weather.WeatherApplication;
import us.kulakov.weather.common.injection.component.DaggerTestComponent;
import us.kulakov.weather.common.injection.component.TestComponent;
import us.kulakov.weather.common.injection.module.ApplicationTestModule;
import us.kulakov.weather.data.WeatherRepository;

/**
 * Test rule that creates and sets a Dagger TestComponent into the application overriding the
 * existing application component. Use this rule in your test case in order for the app to use mock
 * dependencies. It also exposes some of the dependencies so they can be easily accessed from the
 * tests, e.g. to stub mocks etc.
 */
public class TestComponentRule implements TestRule {

    private final TestComponent testComponent;
    private final Context context;

    public TestComponentRule(Context context) {
        this.context = context;
        WeatherApplication application = WeatherApplication.get(context);
        testComponent =
                DaggerTestComponent.builder()
                        .applicationTestModule(new ApplicationTestModule(application))
                        .build();
    }

    public TestComponent getTestComponent() {
        return testComponent;
    }

    public Context getContext() {
        return context;
    }

    public WeatherRepository getMockApiManager() {
        return testComponent.apiManager();
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                WeatherApplication application = WeatherApplication.get(context);
                application.setComponent(testComponent);
                base.evaluate();
                application.setComponent(null);
            }
        };
    }
}
