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
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;
import timber.log.Timber;
import us.kulakov.weather.R;
import us.kulakov.weather.data.application.LatLong;
import us.kulakov.weather.data.application.MultiDayForecast;
import us.kulakov.weather.features.base.BaseActivity;
import us.kulakov.weather.features.common.ErrorView;
import us.kulakov.weather.features.detail.DetailActivity;
import us.kulakov.weather.injection.component.ActivityComponent;

public class MainActivity extends BaseActivity implements MainMvpView, ErrorView.ErrorListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final String NEEDED_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;

    @Inject
    ForecastListAdapter forecastListAdapter;
    @Inject
    MainPresenter mainPresenter;

    @Inject
    ReactiveLocationProvider rxLocationManager;

    @BindView(R.id.view_error)
    ErrorView errorView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.recycler_pokemon)
    RecyclerView pokemonRecycler;

    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Nullable
    private Disposable locationDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);

        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary);
        swipeRefreshLayout.setColorSchemeResources(R.color.white);
        swipeRefreshLayout.setOnRefreshListener(this::subscribeLocationUpdates);

        pokemonRecycler.setLayoutManager(new LinearLayoutManager(this));
        pokemonRecycler.setAdapter(forecastListAdapter);
        subscribeForecastTap();
        checkLocationPermission();
        errorView.setErrorListener(this);
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
                    unsubscribeLocationUpdates();
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
            // We can also subscribe here to continuous updates but we're operating in the foreground
            // with manual refresh, it's a waste of battery.
            locationDisposable = rxLocationManager.getLastKnownLocation()
                    .subscribe(location -> {
                                Timber.d("Got location: %s", location.toString());
                                mainPresenter.fetchWeatherData(new LatLong(location.getLatitude(), location.getLongitude()));
                            },
                            throwable -> Timber.e(throwable, "Failed to observe location"));
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
                                    Timber.e(throwable, "Pokemon click failed");
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
        forecastListAdapter.setPokemon(forecast.forecastList);
        pokemonRecycler.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            if (pokemonRecycler.getVisibility() == View.VISIBLE
                    && forecastListAdapter.getItemCount() > 0) {
                swipeRefreshLayout.setRefreshing(true);
            } else {
                progressBar.setVisibility(View.VISIBLE);

                pokemonRecycler.setVisibility(View.GONE);
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
        pokemonRecycler.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        Timber.e(error, "There was an error retrieving the pokemon");
    }

    @Override
    public void onReloadData() {
        subscribeLocationUpdates();
    }
}
