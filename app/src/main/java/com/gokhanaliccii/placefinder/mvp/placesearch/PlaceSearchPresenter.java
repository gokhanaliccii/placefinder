package com.gokhanaliccii.placefinder.mvp.placesearch;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.gokhanaliccii.placefinder.App;
import com.gokhanaliccii.placefinder.Interactor;
import com.gokhanaliccii.placefinder.api.SearchAPI;
import com.gokhanaliccii.placefinder.model.VenuesListResponse;
import com.gokhanaliccii.placefinder.mvp.placesearch.PlaceSearchContact.Presenter;
import com.gokhanaliccii.placefinder.utility.StringValidator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by gokhan on 04/02/17.
 */

public class PlaceSearchPresenter implements Presenter<PlaceSearchContact.View> {

    public static final String TAG = "PlaceSearchPresenter";

    PlaceSearchContact.View mView;


    @Override
    public void attachView(PlaceSearchContact.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void searchPlace(final String type, String where, Location location) {
        //check PlaceTpye
        boolean valid = StringValidator.isValidPlaceType(type);
        if (!StringValidator.isValidPlaceType(type)) {

            this.mView.showWarnUserInput(null);
            return;
        }

        //location not available
        if ((where == null || where.isEmpty()) && location == null) {

            this.mView.showTypeLocationForcelyPopup();
            return;
        }

        //show user loading popup
        this.mView.showPlaceSearchingPopup();

        Retrofit mRetrofit = Interactor.restInteractor().retrofit();
        SearchAPI searchAPI = mRetrofit.create(SearchAPI.class);

        //search by user input
        if (where != null && !where.isEmpty()) {

            searchAPI.getVenues(App.FOURSQUORE_VERSION, App.FOURSQUORE_CLIENT_ID, App.FOURSQUORE_SECRET_ID, type, where).enqueue(new Callback<VenuesListResponse>() {
                @Override
                public void onResponse(Call<VenuesListResponse> call, Response<VenuesListResponse> response) {

                    Log.i(TAG, "searchPlace getVenues() onResponse");

                    if (response == null) {

                        mView.showSearchResult(type, null);
                        return;
                    }

                    VenuesListResponse fourSquareResponse = response.body();
                    if (fourSquareResponse == null) {

                        mView.showSearchResult(type, null);
                        return;
                    }

                    mView.showSearchResult(type, fourSquareResponse.getResponse());
                }

                @Override
                public void onFailure(Call<VenuesListResponse> call, Throwable t) {

                    Log.e(TAG, "searchPlace getVenues() onFailure " + t.getMessage());

                    mView.showApiFailError();
                }
            });
            return;
        }

        //search by location
        String latLng = String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude());
        searchAPI.getVenuesByLocation(App.FOURSQUORE_VERSION, App.FOURSQUORE_CLIENT_ID, App.FOURSQUORE_SECRET_ID, type, latLng).enqueue(new Callback<VenuesListResponse>() {
            @Override
            public void onResponse(Call<VenuesListResponse> call, Response<VenuesListResponse> response) {

                Log.i(TAG, "searchPlace getVenuesByLocation() onResponse");

                VenuesListResponse fourSquareResponse = response.body();
                mView.showSearchResult(type, fourSquareResponse.getResponse());
            }

            @Override
            public void onFailure(Call<VenuesListResponse> call, Throwable t) {

                Log.e(TAG, "searchPlace getVenuesByLocation() onFailure");
                mView.showApiFailError();
            }
        });
    }

    @Override
    public void storeValues(Bundle bundle) {
        /** NOT NEED ON THERE **/
    }
}