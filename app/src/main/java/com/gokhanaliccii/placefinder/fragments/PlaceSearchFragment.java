package com.gokhanaliccii.placefinder.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gokhanaliccii.placefinder.R;
import com.gokhanaliccii.placefinder.enums.PermissionType;
import com.gokhanaliccii.placefinder.events.PermissionGrantEvent;
import com.gokhanaliccii.placefinder.events.RefreshLocationEvent;
import com.gokhanaliccii.placefinder.events.ShowVenuesListEvent;
import com.gokhanaliccii.placefinder.model.ResponseVenues;
import com.gokhanaliccii.placefinder.mvp.placesearch.PlaceSearchContact;
import com.gokhanaliccii.placefinder.mvp.placesearch.PlaceSearchPresenter;
import com.gokhanaliccii.placefinder.utility.LocationUtility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PlaceSearchFragment extends Fragment implements PlaceSearchContact.View {

    public static final String TAG = "PlaceSearchFragment";
    public static final String CURRENT_LOCATION = "current_location";

    PlaceSearchPresenter mPresenter;

    @BindView(R.id.edit_text_place_search_place_type)
    AppCompatEditText mPlaceTypeEditText;

    @BindView(R.id.edit_text_place_search_where)
    AppCompatEditText mPlaceWhereEditText;

    @BindView(R.id.button_place_search)
    Button mSearchButton;

    private Location mCurrentLocation;

    MaterialDialog mProgressbar;

    public PlaceSearchFragment() {
    }

    public static PlaceSearchFragment NewInstance() {

        return new PlaceSearchFragment();
    }

    public static PlaceSearchFragment NewInstance(Location mCurrentLocation) {

        if (mCurrentLocation == null)
            return new PlaceSearchFragment();

        PlaceSearchFragment placeSearchFragment = new PlaceSearchFragment();
        Bundle mBundle = new Bundle();
        mBundle.putParcelable(CURRENT_LOCATION, mCurrentLocation);
        placeSearchFragment.setArguments(mBundle);

        return placeSearchFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {

        EventBus.getDefault().unregister(this);
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
            mCurrentLocation = getArguments().getParcelable(CURRENT_LOCATION);

        //create our presenter
        mPresenter = new PlaceSearchPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_place_search, container, false);
        ButterKnife.bind(this, view);

        initView();

        return view;
    }

    @Override
    public void showSearchResult(String title, ResponseVenues response) {

        hideLoading();

        //open fragment
        EventBus.getDefault().post(new ShowVenuesListEvent(response, title));
    }

    @Override
    public void showPlaceSearchingPopup() {

        mProgressbar = new MaterialDialog.Builder(getContext())
                .progress(true, 0).content(R.string.searching).cancelable(false)
                .show();
    }

    @Override
    public void showWarnUserInput(String warnText) {

        new MaterialDialog.Builder(getContext()).title(R.string.popup_invalid_input_title).content(R.string.popup_invalid_input_content).positiveText(R.string.ok).show();
    }

    @Override
    public void showTypeLocationForcelyPopup() {

        new MaterialDialog.Builder(getContext()).title(R.string.popup_location_not_found_title).content(R.string.popup_location_not_found_content).show();
    }

    @Override
    public void showNeedGpsPopup() {

        openGpsPopup();
    }

    @Override
    public void showApiFailError() {

        new MaterialDialog.Builder(getContext()).title(R.string.popup_search_fail_title).content(R.string.popup_search_fail_content).positiveText(R.string.ok).show();
    }

    @Override
    public void initView() {

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String placeType = mPlaceTypeEditText.getText().toString();
                String where = mPlaceWhereEditText.getText().toString();

                mPresenter.searchPlace(placeType, where, mCurrentLocation);
            }
        });
    }

    @Override
    public void onAttach() {

    }

    private void openGpsPopup() {

        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshLocationEvent event) {

        refreshLocation();
    }

    private void refreshLocation() {

        mCurrentLocation = LocationUtility.getCurrentLocation((LocationManager) (getActivity().getSystemService(Context.LOCATION_SERVICE)));
    }


    private void hideLoading() {

        if (mProgressbar != null)
            mProgressbar.dismiss();

    }


}