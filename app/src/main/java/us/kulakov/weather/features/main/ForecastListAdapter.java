package us.kulakov.weather.features.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import timber.log.Timber;
import us.kulakov.weather.R;
import us.kulakov.weather.data.application.DayForecast;
import us.kulakov.weather.format.DateUtils;
import us.kulakov.weather.format.PreferredUnitProvider;
import us.kulakov.weather.injection.ActivityContext;

public class ForecastListAdapter extends RecyclerView.Adapter<ForecastListAdapter.ForecastViewHolder> {

    private List<DayForecast> forecasts;
    private Subject<String> forecastClickSubject;
    private PreferredUnitProvider unitProvider;
    private Context context;
    private DateUtils dateUtils;

    @Inject
    ForecastListAdapter(PreferredUnitProvider unitProvider, @ActivityContext Context context,
                        DateUtils dateUtils) {
        this.unitProvider = unitProvider;
        this.context = context;
        this.dateUtils = dateUtils;
        forecastClickSubject = PublishSubject.create();
        forecasts = Collections.emptyList();
    }

    public void setForecasts(List<DayForecast> forecasts) {
        this.forecasts = forecasts;
        notifyDataSetChanged();
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_forecast, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        DayForecast forecast = this.forecasts.get(position);
        holder.onBind(forecast);
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }

    Observable<String> observeForecastTap() {
        return forecastClickSubject;
    }

    class ForecastViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_temp_high)
        TextView tempHighText;

        @BindView(R.id.text_temp_low)
        TextView tempLowText;

        @BindView(R.id.text_display_time)
        TextView textTime;

        @BindView(R.id.image_forecast)
        ImageView forecastImage;

        private DayForecast forecast;

        ForecastViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> forecastClickSubject.onNext(forecast.forecastId));
        }

        void onBind(DayForecast forecast) {
            this.forecast = forecast;

            tempHighText.setText(unitProvider.getFormattedTempFromK(forecast.tempMax));
            tempLowText.setText(unitProvider.getFormattedTempFromK(forecast.tempMin));

            if (forecast.timestamp != null) {
                textTime.setText(dateUtils.formatDisplayWeekday(forecast.timestamp));
            } else {
                Timber.d("Warning: Timestamp for forecast was not set");
            }

            if (forecast.imageURL != null) {
                Glide.with(context)
                        .load(forecast.imageURL)
                        .placeholder(R.drawable.progress_animation)
                        .into(forecastImage);
            }
        }
    }
}
