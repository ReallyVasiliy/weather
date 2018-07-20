package us.kulakov.weather.format;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by Vasiliy Kulakov on 7/18/18.
 * This file is part of the project "weather".
 * Copyright (c) 2018 Vasiliy Kulakov
 */
public class DateUtils {

    private final SimpleDateFormat format = new SimpleDateFormat("EEEE, MMM d", Locale.getDefault());

    @Inject
    public DateUtils() {
    }

    @NonNull
    public String formatDisplayWeekday(@NonNull Long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp * 1000L);
        return format.format(cal.getTime());
//        String day = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
//        return day;
    }
}
