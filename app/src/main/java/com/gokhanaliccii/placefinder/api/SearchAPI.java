package com.gokhanaliccii.placefinder.api;

import com.gokhanaliccii.placefinder.model.ResponseVenue;
import com.gokhanaliccii.placefinder.model.VenueDetailResponse;
import com.gokhanaliccii.placefinder.model.VenuesListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by gokhan on 04/02/17.
 */

public interface SearchAPI {

    @GET("/v2/venues/search")
    Call<VenuesListResponse> getVenues(@Query("v") String version, @Query("client_id") String clientID, @Query("client_secret") String clientSecret, @Query("query") String placeType, @Query("near") String near);

    @GET("/v2/venues/search")
    Call<VenuesListResponse> getVenuesByLocation(@Query("v") String version, @Query("client_id") String clientID, @Query("client_secret") String clientSecret, @Query("query") String placeType, @Query("ll") String latLng);

    @GET("/v2/venues/{venue_id}")
    Call<VenueDetailResponse> getVenueDetail(@Path("venue_id") String venueID, @Query("v") String version, @Query("client_id") String clientID, @Query("client_secret") String clientSecret);
}
