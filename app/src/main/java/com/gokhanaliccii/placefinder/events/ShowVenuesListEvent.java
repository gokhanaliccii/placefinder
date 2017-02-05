package com.gokhanaliccii.placefinder.events;

import com.gokhanaliccii.placefinder.model.ResponseVenues;

/**
 * Created by gokhan on 04/02/17.
 */

public class ShowVenuesListEvent {
    private String title;
    private ResponseVenues mResponse;

    public ShowVenuesListEvent(ResponseVenues mResponse) {
        this.mResponse = mResponse;
    }

    public ShowVenuesListEvent(ResponseVenues mResponse,String title) {

        this.mResponse = mResponse;
        this.title=title;
    }

    public ResponseVenues getResponse() {

        return mResponse;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}