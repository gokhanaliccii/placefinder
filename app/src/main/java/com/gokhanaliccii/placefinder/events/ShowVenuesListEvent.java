package com.gokhanaliccii.placefinder.events;

import com.gokhanaliccii.placefinder.model.FourSquareResponse;

/**
 * Created by gokhan on 04/02/17.
 */

public class ShowVenuesListEvent {
    private FourSquareResponse mResponse;

    public ShowVenuesListEvent(FourSquareResponse mResponse) {
        this.mResponse = mResponse;
    }

    public FourSquareResponse getmResponse() {
        return mResponse;
    }
}
