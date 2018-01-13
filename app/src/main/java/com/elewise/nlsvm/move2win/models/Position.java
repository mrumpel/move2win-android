package com.elewise.nlsvm.move2win.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.Locale;

/**
 * Created by lucenko on 12.01.2018.
 */

@IgnoreExtraProperties
public class Position implements Parcelable{

    @Exclude
    public static final String BUNDEL_KEY = "position";

    @PropertyName("X")
    public double lat;

    @PropertyName("Y")
    public double lng;

    //Required for firebase
    public Position() {
    }

    public Position(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    protected Position(Parcel in) {
        lat = in.readDouble();
        lng = in.readDouble();
    }

    public static final Creator<Position> CREATOR = new Creator<Position>() {
        @Override
        public Position createFromParcel(Parcel in) {
            return new Position(in);
        }

        @Override
        public Position[] newArray(int size) {
            return new Position[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "lat: %f, lng: %f", lat, lng);
    }
}
