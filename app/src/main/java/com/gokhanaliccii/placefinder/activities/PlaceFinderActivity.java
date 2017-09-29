package com.gokhanaliccii.placefinder.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.gokhanaliccii.placefinder.R;
import com.gokhanaliccii.placefinder.events.RefreshLocationEvent;
import com.gokhanaliccii.placefinder.events.ShowVenueDetailEvent;
import com.gokhanaliccii.placefinder.events.ShowVenuesListEvent;
import com.gokhanaliccii.placefinder.fragments.PlaceListFragment;
import com.gokhanaliccii.placefinder.fragments.PlaceSearchFragment;
import com.gokhanaliccii.placefinder.fragments.VenueDetailFragment;
import com.gokhanaliccii.placefinder.model.ResponseVenues;
import com.gokhanaliccii.placefinder.model.Venue;
import com.gokhanaliccii.placefinder.utility.PermissionUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.INTERNET;

public class PlaceFinderActivity extends AppCompatActivity {

    public static final String TAG = "PlaceFinderActivity";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private boolean doubleTapToExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);

        setContentView(R.layout.activity_placefinder);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        android.location.Location location = null;
        if (getIntent().hasExtra(SplashActivity.CURRENT_LOCATION))
            location = getIntent().getExtras().getParcelable(SplashActivity.CURRENT_LOCATION);

        //Add Search Fragment
        PlaceSearchFragment placeSearchFragment = PlaceSearchFragment.newInstance(location);

        getSupportFragmentManager().beginTransaction().
                add(R.id.place_finder_fragment_content, placeSearchFragment).commit();

        if (!hasAllPermissionsCheck())
            requestNeedPermissions();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EventBus.getDefault().post(new RefreshLocationEvent());
    }

    private void requestNeedPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{INTERNET, ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION},
                1);
    }

    private boolean hasAllPermissionsCheck() {
        boolean grantedAllPermissions = checkPermissionGranted(INTERNET)
                && checkPermissionGranted(ACCESS_FINE_LOCATION)
                && checkPermissionGranted(ACCESS_COARSE_LOCATION);

        return grantedAllPermissions;
    }

    private boolean checkPermissionGranted(String permission) {
        return PermissionUtil.hasPermission(this, permission);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(ShowVenuesListEvent event) {
        if (event == null)
            return;

        showBackButton();
        mToolbar.setTitle(event.getTitle());

        ResponseVenues mResponse = event.getResponse();
        getSupportFragmentManager().beginTransaction().add(R.id.place_finder_fragment_content, PlaceListFragment.NewInstance(mResponse)).addToBackStack(null).commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(ShowVenueDetailEvent event) {

        if (event == null)
            return;

        Venue mVenue = event.getVenue();
        VenueDetailFragment venueDetailFragment = VenueDetailFragment.NewInstance(mVenue);

        venueDetailFragment.show(getSupportFragmentManager(), VenueDetailFragment.TAG);
    }

    @Override
    public void onBackPressed() {

        int bsc = getSupportFragmentManager().getBackStackEntryCount();
        Log.i(TAG, "bsc:" + bsc);

        if (bsc > 0) {

            getSupportFragmentManager().popBackStack();

            if (bsc == 1) {

                hideBackButton();
                mToolbar.setTitle(R.string.toolbar_title);
            }

            return;
        }

        if (doubleTapToExit) {

            super.onBackPressed();
            return;
        }

        doubleTapToExit = true;
        Toast.makeText(getApplicationContext(), R.string.double_tap_for_exit, Toast.LENGTH_SHORT).show();

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleTapToExit = false;
            }
        }, 500);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int menuID = menuItem.getItemId();

        if (menuID == android.R.id.home) {

            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    private void showBackButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void hideBackButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
