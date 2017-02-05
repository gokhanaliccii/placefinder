package com.gokhanaliccii.placefinder;

import android.app.Application;

/**
 * Created by gokhan on 04/02/17.
 */

public class App extends Application {

    public static final String FOURSQUORE_VERSION = "20130815";
    public static final String FOURSQUORE_CLIENT_ID = "AHGMLIAOHLNG1NU2IT525G0RJ2I5PXOCUBVCL5N5EJX4L321";
    public static final String FOURSQUORE_SECRET_ID = "YLULMZUWQ0XIKC2O2HJQFW0NCNCDIRF22C3BSLSJUT24G4Z1";
    public static final String BASE_URL = "https://api.foursquare.com";

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
