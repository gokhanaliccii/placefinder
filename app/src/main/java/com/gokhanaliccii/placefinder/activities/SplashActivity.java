package com.gokhanaliccii.placefinder.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.gokhanaliccii.placefinder.R;
import com.gokhanaliccii.placefinder.utility.LocationUtility;
import com.gokhanaliccii.placefinder.utility.PermissionUtil;


/**
 * Created by gokhan on 04/02/17.
 */

public class SplashActivity extends AppCompatActivity {

    private static final long DELAY = 2000;
    public static final String CURRENT_LOCATION = "current_location";

    private boolean isVisible = true;

    private Location mCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_splash);

        //get current location
        if (PermissionUtil.hasAccessFineLocationPermission(this)) ;
        mCurrentLocation = LocationUtility.getCurrentLocation((LocationManager) getSystemService(Context.LOCATION_SERVICE));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //check is visible
                if (!isVisible)
                    return;

                startPlaceFinderActivity();
            }
        }, DELAY);
    }

    @Override
    protected void onStop() {

        isVisible = false;
        super.onStop();
    }

    private void startPlaceFinderActivity() {

        Intent mFinderActivityIntent = new Intent(getApplicationContext(), PlaceFinderActivity.class);

        if (mCurrentLocation != null)
            mFinderActivityIntent.putExtra(CURRENT_LOCATION, mCurrentLocation);

        startActivity(mFinderActivityIntent);
        finish();
    }
}
