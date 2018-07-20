package us.kulakov.weather;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import us.kulakov.weather.common.TestComponentRule;
import us.kulakov.weather.data.application.DayForecast;
import us.kulakov.weather.data.application.DayForecastBuilder;
import us.kulakov.weather.features.detail.DetailActivity;
import us.kulakov.weather.util.ErrorTestUtil;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {

    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());
    public final ActivityTestRule<DetailActivity> main =
            new ActivityTestRule<>(DetailActivity.class, false, false);

    @Rule
    public TestRule chain = RuleChain.outerRule(component).around(main);

    @Test
    public void checkForecastDisplays() {
        DayForecast forecast = new DayForecastBuilder()
                .setTitle("some title")
                .createDayForecast();
        String id = forecast.forecastId;

        stubDataManagerGetForecast(forecast);

        main.launchActivity(
                DetailActivity.getStartIntent(InstrumentationRegistry.getContext(), id));

        onView(withText(forecast.title)).check(matches(isDisplayed()));
        onView(withText(forecast.description)).check(matches(isDisplayed()));
        // TODO: More checks
    }

    @Test
    public void checkErrorViewDisplays() {
        stubDataManagerGetForecast(null);
        main.launchActivity(
                DetailActivity.getStartIntent(InstrumentationRegistry.getContext(), "0123456789"));
        ErrorTestUtil.checkErrorViewsDisplay();
    }

    public void stubDataManagerGetForecast(DayForecast response) {
        when(component.getMockApiManager().queryCachedForecast(anyString()))
                .thenReturn(response);
    }
}
