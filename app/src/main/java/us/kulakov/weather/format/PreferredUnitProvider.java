package us.kulakov.weather.format;

import android.support.annotation.NonNull;

import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by Vasiliy Kulakov on 7/18/18.
 * This file is part of the project "weather".
 * Copyright (c) 2018 Vasiliy Kulakov
 */
public class PreferredUnitProvider {

    @Inject
    public PreferredUnitProvider() {
    }

    @NonNull
    public UnitSystem getDefaultUnitSystem() {
        return getUnitSystemFromLocale(Locale.getDefault());
    }

    @NonNull
    public UnitSystem getUnitSystemFromLocale(Locale locale) {
        String countryCode = locale.getCountry();
        if ("US".equals(countryCode)) return UnitSystem.IMPERIAL; // USA
        if ("LR".equals(countryCode)) return UnitSystem.IMPERIAL; // Liberia
        if ("MM".equals(countryCode)) return UnitSystem.IMPERIAL; // Myanmar
        return UnitSystem.METRIC;
    }

    @NonNull
    public String getDefaultUnitFromK(Double kelvinDegrees) {
        UnitSystem unit = getDefaultUnitSystem();
        String label = tempUnitShort(unit);
        Double value = UnitConversionUtils.kelvinToUnit(kelvinDegrees, unit);

        if (value != null) {
            // \u00b0 is the degree symbol
            return String.format(Locale.getDefault(), "%.0f\u00b0%s", value, label);
        } else {
            return String.format("%s\u00b0%s", "?", label);
        }
    }

    @NonNull
    public String tempUnitShort(UnitSystem system) {
        // TODO: Does l10n apply here?
        if (system == UnitSystem.IMPERIAL) {
            return "F";
        } else {
            return "C";
        }
    }
}
