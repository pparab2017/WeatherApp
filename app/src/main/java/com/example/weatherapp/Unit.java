package com.example.weatherapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pushparajparab on 10/4/16.
 */
public class Unit implements Parcelable {

    public Unit(String english, String metric) {
        this.english = english;
        this.metric = metric;
    }

    String english,metric;

    protected Unit(Parcel in) {
        english = in.readString();
        metric = in.readString();
    }

    public static final Creator<Unit> CREATOR = new Creator<Unit>() {
        @Override
        public Unit createFromParcel(Parcel in) {
            return new Unit(in);
        }

        @Override
        public Unit[] newArray(int size) {
            return new Unit[size];
        }
    };

    public String getEnglish() {
        return english;
    }

    public String getMetric() {
        return metric;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(english);
        dest.writeString(metric);
    }
}
