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

    @NonNull
    public static String formatDisplayWeekday(@NonNull Long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp * 1000L);
        String day = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        return day;
    }
}
