package us.kulakov.weather.features.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;
import us.kulakov.weather.R;
import us.kulakov.weather.data.application.DayForecast;
import us.kulakov.weather.features.base.BaseActivity;
import us.kulakov.weather.features.common.ErrorView;
import us.kulakov.weather.format.DateUtils;
import us.kulakov.weather.format.PreferredUnitProvider;
import us.kulakov.weather.format.UnitConversionUtils;
import us.kulakov.weather.injection.component.ActivityComponent;

public class DetailActivity extends BaseActivity implements DetailMvpView, ErrorView.ErrorListener {

    public static final String EXTRA_FORECAST_ID = "EXTRA_FORECAST_ID";

    @Inject
    DetailPresenter detailPresenter;

    @Inject
    DateUtils dateUtils;

    @Inject
    PreferredUnitProvider unitProvider;

    @BindView(R.id.view_error)
    ErrorView errorView;

    @BindView(R.id.image_forecast)
    ImageView forecastImage;

    @BindView(R.id.progress)
    ProgressBar progress;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.layout_forecast)
    View forecastLayout;

    @BindView(R.id.text_title)
    TextView forecastTitle;

    @BindView(R.id.text_description)
    TextView forecastDescription;

    @BindView(R.id.text_high)
    TextView forecastHigh;

    @BindView(R.id.text_low)
    TextView forecastLow;

    @BindView(R.id.text_humidity)
    TextView forecastHumidity;


    private String forecastId;

    public static Intent getStartIntent(Context context, String forecastId) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_FORECAST_ID, forecastId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        forecastId = getIntent().getStringExtra(EXTRA_FORECAST_ID);
        if (forecastId == null) {
            throw new IllegalArgumentException("Detail Activity requires a forecast ID");
        }

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        errorView.setErrorListener(this);

        detailPresenter.getForecast(forecastId);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void attachView() {
        detailPresenter.attachView(this);
    }

    @Override
    protected void detachPresenter() {
        detailPresenter.detachView();
    }

    @Override
    public void showWeather(DayForecast forecast) {
        forecastLayout.setVisibility(View.VISIBLE);

        if (forecast.timestamp != null) {
            setTitle(dateUtils.formatForecastDisplayDate(forecast.timestamp));
        }

        // TODO: If we add any more data here, switch to data binding, this is already out of hand

        if (forecast.imageURL != null) {
            Glide.with(this)
                    .load(forecast.imageURL)
                    .placeholder(R.drawable.progress_animation)
                    .into(forecastImage);
        }

        if (forecast.title != null) {
            forecastTitle.setText(forecast.title);
        }

        if (forecast.description != null) {
            forecastDescription.setText(forecast.description);
        }

        if (forecast.tempMax != null) {
            forecastHigh.setText(unitProvider.getFormattedTempFromK(forecast.tempMax));
        }

        if (forecast.tempMin != null) {
            forecastLow.setText(unitProvider.getFormattedTempFromK(forecast.tempMin));
        }

        if (forecast.humidity != null) {
            forecastHumidity.setText(getString(R.string.humidity_format, forecast.humidity));
        }
    }

    @Override
    public void showProgress(boolean show) {
        errorView.setVisibility(View.GONE);
        progress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(Throwable error) {
        forecastLayout.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        Timber.e(error, "There was a problem retrieving the forecast...");
    }

    @Override
    public void onReloadData() {
        detailPresenter.getForecast(forecastId);
    }
}
