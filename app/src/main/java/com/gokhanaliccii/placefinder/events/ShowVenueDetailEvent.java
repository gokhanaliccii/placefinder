package com.gokhanaliccii.placefinder.events;

import com.gokhanaliccii.placefinder.model.Venue;

/**
 * Created by gokhan on 05/02/17.
 */

public class ShowVenueDetailEvent {

    Venue mVenue;

    public ShowVenueDetailEvent(Venue mVenue) {
        this.mVenue = mVenue;
    }

    public Venue getVenue() {
        return mVenue;
    }
}
