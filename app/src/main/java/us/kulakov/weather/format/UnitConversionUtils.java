package us.kulakov.weather.format;

import timber.log.Timber;

/**
 * Created by Vasiliy Kulakov on 7/18/18.
 * This file is part of the project "weather".
 * Copyright (c) 2018 Vasiliy Kulakov
 */
public class UnitConversionUtils {
    public static Double kelvinToUnit(Double k, UnitSystem unit) {
        switch (unit) {
            case METRIC:
                return k != null ? k - 273.15 : null;
            case IMPERIAL:
                return k != null ? k * (9./5.) - 459.67 : null;
        }
        Timber.w(String.format("Unhandled unit %s while converting", unit == null ? unit : unit.name()));
        return k;
    }
}
