package com.gokhanaliccii.placefinder.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gokhan on 04/02/17.
 */

public class Location implements Parcelable {
    private float lat;
    private float lng;
    String address;
    String country;
    String city;
    String state;


    public Location() {
    }

    public String getAddress() {

        return address;
    }

    public String getCountry() {

        return country;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {

        return lng;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getReadableLoc() {

        if (state != null && city != null)
            return state + "/" + city;

        if (state != null)
            return state;

        return country;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.lat);
        dest.writeFloat(this.lng);
        dest.writeString(this.address);
        dest.writeString(this.country);
        dest.writeString(this.city);
        dest.writeString(this.state);
    }

    protected Location(Parcel in) {
        this.lat = in.readFloat();
        this.lng = in.readFloat();
        this.address = in.readString();
        this.country = in.readString();
        this.city = in.readString();
        this.state = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
