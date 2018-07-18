package us.kulakov.weather.injection.component;

import dagger.Subcomponent;
import us.kulakov.weather.injection.PerFragment;
import us.kulakov.weather.injection.module.FragmentModule;

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
}
