package com.gokhanaliccii.placefinder.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gokhanaliccii.placefinder.R;
import com.gokhanaliccii.placefinder.model.Venue;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gokhan on 04/02/17.
 */

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.PlaceViewHolder> {

    private ArrayList<Venue> mVenuesList;

    public PlaceListAdapter(ArrayList<Venue> mVenuesList) {

        this.mVenuesList = mVenuesList;
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_place_item, parent, false);

        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {

        if (holder == null)
            return;

        Venue venue = mVenuesList.get(position);
        if (venue == null)
            return;

        holder.mTitle.setText(venue.getName());
        holder.mAddress.setText(venue.getAddress());
        holder.mCountry.setText(venue.getLocation().getReadableLoc());
    }

    @Override
    public int getItemCount() {

        if (mVenuesList == null)
            return 0;

        return mVenuesList.size();
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_place_card_title)
        AppCompatTextView mTitle;

        @BindView(R.id.tv_place_card_address)
        AppCompatTextView mAddress;

        @BindView(R.id.tv_place_card_country)
        AppCompatTextView mCountry;

        public PlaceViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
