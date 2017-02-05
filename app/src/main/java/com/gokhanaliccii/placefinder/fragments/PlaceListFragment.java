package com.gokhanaliccii.placefinder.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gokhanaliccii.placefinder.R;
import com.gokhanaliccii.placefinder.adapters.PlaceListAdapter;
import com.gokhanaliccii.placefinder.adapters.RecyclerItemClickListener;
import com.gokhanaliccii.placefinder.events.ShowVenueDetailEvent;
import com.gokhanaliccii.placefinder.model.ResponseVenues;
import com.gokhanaliccii.placefinder.model.Venue;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PlaceListFragment extends Fragment {

    public static final String TAG = "PlaceListFragment";
    public static final String RESPONSE_OBJ = "response_obj";

    @BindView(R.id.recyclerview_placelist)
    RecyclerView mPlacesRecyclerView;

    private PlaceListAdapter mAdapter;
    private ResponseVenues mResponse;


    public PlaceListFragment() {
    }

    public static PlaceListFragment NewInstance(ResponseVenues response) {

        Bundle mBundle = new Bundle();
        mBundle.putParcelable(RESPONSE_OBJ, response);

        PlaceListFragment placeListFragment = new PlaceListFragment();
        placeListFragment.setArguments(mBundle);

        return placeListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
            mResponse = getArguments().getParcelable(RESPONSE_OBJ);

        if (mResponse == null)
            Log.e(TAG, "response obj is null");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_place_list, container, false);

        ButterKnife.bind(this, view);

        initView();

        return view;
    }

    private void initView() {

        mAdapter = new PlaceListAdapter(mResponse.getVenues());
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);

        mPlacesRecyclerView.addItemDecoration(itemDecoration);
        mPlacesRecyclerView.setLayoutManager(mLinearLayoutManager);
        mPlacesRecyclerView.setAdapter(mAdapter);


        mPlacesRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mPlacesRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Venue selectedVenue = mResponse.getVenue(position);
                EventBus.getDefault().post(new ShowVenueDetailEvent(selectedVenue));
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

        }));

    }

}
