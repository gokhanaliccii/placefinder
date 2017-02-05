package com.gokhanaliccii.placefinder.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gokhanaliccii.placefinder.App;
import com.gokhanaliccii.placefinder.R;
import com.gokhanaliccii.placefinder.adapters.UserCommentAdapter;
import com.gokhanaliccii.placefinder.api.SearchAPI;
import com.gokhanaliccii.placefinder.model.Location;
import com.gokhanaliccii.placefinder.model.Photo;
import com.gokhanaliccii.placefinder.model.ResponseVenue;
import com.gokhanaliccii.placefinder.model.TipGroup;
import com.gokhanaliccii.placefinder.model.Tips;
import com.gokhanaliccii.placefinder.model.UserComment;
import com.gokhanaliccii.placefinder.model.Venue;
import com.gokhanaliccii.placefinder.model.VenueDetailResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gokhan on 05/02/17.
 */

public class VenueDetailFragment extends DialogFragment implements OnMapReadyCallback {

    public static final String TAG = "VenueDetailFragment";
    public static final String VENUE_OBJ = "venue_obj";

    Venue mVenue;

    @BindView(R.id.recyclerview_user_comments)
    RecyclerView mCommentRecyclerView;

    @BindView(R.id.progress_venue_detail_image)
    ProgressBar mProgressBar;

    @BindView(R.id.image_venue_detail_venue_icon)
    ImageView mVenueIcon;

    @BindView(R.id.card_venue_detail_venue_name)
    TextView mVenueName;

    @BindView(R.id.map_venue_detail)
    MapView mMap;


    UserCommentAdapter mUserCommentAdapter;
    LinearLayoutManager linearLayoutManager;


    public static VenueDetailFragment NewInstance(Venue mVenue) {

        VenueDetailFragment venueDetailFragment = new VenueDetailFragment();

        Bundle mBundle = new Bundle();
        mBundle.putParcelable(VENUE_OBJ, mVenue);

        venueDetailFragment.setArguments(mBundle);

        return venueDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.VenueDialogStyle);

        if (getArguments() != null)
            mVenue = getArguments().getParcelable(VENUE_OBJ);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_venue_detail, container, false);
        ButterKnife.bind(this, view);

        linearLayoutManager = new LinearLayoutManager(getContext());
        mCommentRecyclerView.setLayoutManager(linearLayoutManager);

        mUserCommentAdapter = new UserCommentAdapter(null);
        mCommentRecyclerView.setAdapter(mUserCommentAdapter);


        mVenueName.setText(mVenue.getName());


        getVenueDetail();

        MapsInitializer.initialize(getContext());
        mMap.onCreate(savedInstanceState);
        mMap.getMapAsync(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMap.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMap.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMap.onDestroy();
    }

    private void getVenueDetail() {

        Retrofit mRetrofit = new Retrofit.Builder().baseUrl(App.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        SearchAPI searchAPI = mRetrofit.create(SearchAPI.class);

        searchAPI.getVenueDetail(mVenue.getId(), App.FOURSQUORE_VERSION, App.FOURSQUORE_CLIENT_ID, App.FOURSQUORE_SECRET_ID).enqueue(new Callback<VenueDetailResponse>() {
            @Override
            public void onResponse(Call<VenueDetailResponse> call, Response<VenueDetailResponse> response) {


                VenueDetailResponse venueDetailResponse = response.body();
                ResponseVenue responseVenue = venueDetailResponse.getResponse();
                Venue venue = responseVenue.getVenue();

                showPlaceImage(venue.getBestPhoto());

                Tips tips = venue.getTips();
                ArrayList<TipGroup> groups = tips.getGroups();

                ArrayList<UserComment> userComments = groups.get(0).getItems();
                refreshComments(userComments);


                Log.i(TAG, "onResponse");
            }

            @Override
            public void onFailure(Call<VenueDetailResponse> call, Throwable t) {

                Log.i(TAG, "onFail");
            }
        });

    }

    private void refreshComments(ArrayList<UserComment> comments) {

        mUserCommentAdapter.setUserComments(comments);
        mUserCommentAdapter.notifyItemChanged(comments.size());
    }

    private void showPlaceImage(Photo photo) {

        if (photo == null)
            return;

        String photoUrl = photo.getImageBySize("400x400");
        if (photoUrl == null)
            return;

        Glide.with(this).load(photoUrl).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                mProgressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                mProgressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(mVenueIcon).getRequest();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.i(TAG, "onMapReady");
        Location location = mVenue.getLocation();

        googleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLat(), location.getLng())).title(""));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLat(), location.getLng()), 14));
    }
}
