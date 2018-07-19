package us.kulakov.weather.data.application;

import android.location.Location;

import java.util.Locale;

/**
 * Created by Vasiliy Kulakov on 7/18/18.
 * This file is part of the project "weather".
 * Copyright (c) 2018 Vasiliy Kulakov
 */
public class LatLong {
    public final double latitude;
    public final double longitude;

    public LatLong(final double latitude, final double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LatLong)) {
            return false;
        }

        float[] distance = new float[1];
        Location.distanceBetween(latitude, longitude, ((LatLong)o).latitude, ((LatLong)o).longitude, distance);
        if (distance[0] < 5.0) {
            return true;
        }
        return false;
    }
}
