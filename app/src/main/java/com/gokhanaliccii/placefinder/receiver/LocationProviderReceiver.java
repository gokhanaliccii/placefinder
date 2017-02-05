package com.gokhanaliccii.placefinder.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import com.gokhanaliccii.placefinder.events.RefreshLocationEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by gokhan on 05/02/17.
 */

public class LocationProviderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {

            LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                EventBus.getDefault().post(new RefreshLocationEvent());
        }
    }
}
