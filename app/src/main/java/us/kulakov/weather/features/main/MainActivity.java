package us.kulakov.weather.features.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;
import timber.log.Timber;
import us.kulakov.weather.R;
import us.kulakov.weather.data.application.CurrentWeather;
import us.kulakov.weather.data.application.LatLong;
import us.kulakov.weather.data.application.MultiDayForecast;
import us.kulakov.weather.features.base.BaseActivity;
import us.kulakov.weather.features.common.ErrorView;
import us.kulakov.weather.features.detail.DetailActivity;
import us.kulakov.weather.format.PreferredUnitProvider;
import us.kulakov.weather.injection.component.ActivityComponent;

public class MainActivity extends BaseActivity implements MainMvpView, ErrorView.ErrorListener, OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final String NEEDED_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final long LOCATION_TIMEOUT_IN_SECONDS = 5;
    private static final long LOCATION_UPDATE_INTERVAL = 5;
    private static final float LOCATION_FOUND_MAP_ZOOM = 10.0f;

    @Inject
    ForecastListAdapter forecastListAdapter;
    @Inject
    MainPresenter mainPresenter;

    @Inject
    ReactiveLocationProvider rxLocationManager;

    @Inject
    PreferredUnitProvider unitProvider;

    @BindView(R.id.view_error)
    ErrorView errorView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.recycler_forecast)
    RecyclerView forecastRecycler;

    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.text_title)
    TextView currentTitle;

    @BindView(R.id.text_location_current)
    TextView currentLocation;

    @BindView(R.id.text_temp)
    TextView currentTemp;

    @BindView(R.id.text_humidity)
    TextView currentHumidity;

    @BindView(R.id.image_conditions)
    ImageView currentConditionsImage;

    @Nullable
    private Disposable locationDisposable;

    @Nullable
    private GoogleMap map;

    @Nullable
    private Marker currentMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);

        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary);
        swipeRefreshLayout.setColorSchemeResources(R.color.white);
        swipeRefreshLayout.setOnRefreshListener(this::subscribeLocationUpdates);

        forecastRecycler.setLayoutManager(new LinearLayoutManager(this));
        forecastRecycler.setAdapter(forecastListAdapter);
        subscribeForecastTap();
        checkLocationPermission();
        errorView.setErrorListener(this);


        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, NEEDED_PERMISSION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, NEEDED_PERMISSION)) {

                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{NEEDED_PERMISSION}, LOCATION_PERMISSION_REQUEST_CODE);
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{NEEDED_PERMISSION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            subscribeLocationUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, NEEDED_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
                        subscribeLocationUpdates();
                    }
                } else {
                    checkLocationPermission();
                }
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, NEEDED_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            subscribeLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this, NEEDED_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            unsubscribeLocationUpdates();
        }
    }

    private void unsubscribeLocationUpdates() {
        if (locationDisposable != null && !locationDisposable.isDisposed()) {
            locationDisposable.dispose();
            locationDisposable = null;
        }
    }

    private void subscribeLocationUpdates() {
        unsubscribeLocationUpdates();

        if (ContextCompat.checkSelfPermission(this, NEEDED_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            // We really just need location once per call
            LocationRequest req = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setExpirationDuration(TimeUnit.SECONDS.toMillis(LOCATION_TIMEOUT_IN_SECONDS))
                    .setInterval(LOCATION_UPDATE_INTERVAL);

            locationDisposable = rxLocationManager.getUpdatedLocation(req)
                    .timeout(LOCATION_TIMEOUT_IN_SECONDS,
                            TimeUnit.SECONDS,
                            AndroidSchedulers.mainThread(),
                            rxLocationManager.getLastKnownLocation())
                    .firstOrError()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(location -> {
                                Timber.d("Got location: %s", location.toString());
                                onLocationUpdated(new LatLong(location.getLatitude(), location.getLongitude()));
                            },
                            throwable -> Timber.e(throwable, "Failed to observe location"));
        }
    }

    private void onLocationUpdated(@NonNull LatLong latLong) {
        mainPresenter.fetchForecast(latLong);
        mainPresenter.fetchCurrentWeather(latLong);

        if (map != null) {
            LatLng gapiTarget = new LatLng(latLong.latitude, latLong.longitude);

            CameraPosition position = CameraPosition.builder()
                    .target(gapiTarget)
                    .zoom(LOCATION_FOUND_MAP_ZOOM)
                    .build();

            CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);

            map.animateCamera(update);

            placeMapMarker(gapiTarget);
        }
    }

    private void subscribeForecastTap() {
        Disposable disposable =
                forecastListAdapter
                        .observeForecastTap()
                        .subscribe(
                                forecast ->
                                        startActivity(DetailActivity.getStartIntent(this, forecast)),
                                throwable -> {
                                    Timber.e(throwable, "Forecast tap failed");
                                    Toast.makeText(
                                            this,
                                            R.string.error_something_bad_happened,
                                            Toast.LENGTH_LONG)
                                            .show();
                                });
        mainPresenter.addDisposable(disposable);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void attachView() {
        mainPresenter.attachView(this);
    }

    @Override
    protected void detachPresenter() {
        mainPresenter.detachView();
    }

    @Override
    public void showForecast(MultiDayForecast forecast) {
        forecastListAdapter.setForecasts(forecast.forecastList);
        forecastRecycler.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCurrentWeather(CurrentWeather weather) {
        // TODO: Switch this over to use data binding instead
        if (weather.temp != null) {
            currentTemp.setText(unitProvider.getFormattedTempFromK(weather.temp));
        } else {
            currentTemp.setText(R.string.no_value);
        }

        if (weather.humidity != null) {
            currentHumidity.setText(getString(R.string.humidity_format, (double)weather.humidity));
        } else {
            currentHumidity.setText(R.string.no_value);
        }

        if (weather.locationName != null) {
            currentLocation.setText(getString(R.string.current_location_format, weather.locationName));
        } else {
            currentLocation.setText(R.string.current_location_none);
        }

        if (weather.title != null) {
            currentTitle.setText(weather.title);
        } else {
            currentTitle.setText(R.string.no_value);
        }

        if (weather.imageURL != null) {
            Glide.with(this)
                    .load(weather.imageURL)
                    .placeholder(R.drawable.progress_animation)
                    .into(currentConditionsImage);
        }
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            if (forecastRecycler.getVisibility() == View.VISIBLE
                    && forecastListAdapter.getItemCount() > 0) {
                swipeRefreshLayout.setRefreshing(true);
            } else {
                progressBar.setVisibility(View.VISIBLE);

                forecastRecycler.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.GONE);
            }

            errorView.setVisibility(View.GONE);
        } else {
            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(Throwable error) {
        forecastRecycler.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        Timber.e(error, "There was an error retrieving the forecast");
    }

    @Override
    public void onReloadData() {
        subscribeLocationUpdates();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    private void placeMapMarker(LatLng position) {
        if (map == null) {
            Timber.w("Placing marker before map is ready");
        }

        if (currentMarker != null) {
            currentMarker.remove();
        }

        currentMarker = map.addMarker(new MarkerOptions().position(position).title("Your Location"));
    }
}
