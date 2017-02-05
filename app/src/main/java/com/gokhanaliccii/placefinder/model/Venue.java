package com.gokhanaliccii.placefinder.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gokhan on 04/02/17.
 */

public class Venue implements Parcelable {
    private String id;
    private String name;
    private Location location;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeParcelable(this.location, flags);
    }

    public Venue() {
    }

    protected Venue(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.location = in.readParcelable(Location.class.getClassLoader());
    }

    public static final Parcelable.Creator<Venue> CREATOR = new Parcelable.Creator<Venue>() {
        @Override
        public Venue createFromParcel(Parcel source) {
            return new Venue(source);
        }

        @Override
        public Venue[] newArray(int size) {
            return new Venue[size];
        }
    };
}