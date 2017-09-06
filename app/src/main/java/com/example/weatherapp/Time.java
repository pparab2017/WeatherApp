package com.example.weatherapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by pushparajparab on 10/4/16.
 */
public class Time implements Parcelable {
    protected Time(Parcel in) {
        preetyTime = (Date)in.readValue(Date.class.getClassLoader());
    }

    public Time(String preetyTime) {
        try {
            //11:00 PM EDT on October 04, 2016
            if(preetyTime.contains("IST")){
                preetyTime = preetyTime.replace("IST","EDT");
            }
            DateFormat format = new SimpleDateFormat("h:mm a z 'on' MMMMM dd, yyyy", Locale.ENGLISH);
            Date date = format.parse(preetyTime);
            this.preetyTime = date;
        }//3:30 AM IST on October 06, 2016
        catch (Exception e)
        {
            Log.d("debug",e.getMessage());
        }
    }

    public static final Creator<Time> CREATOR = new Creator<Time>() {
        @Override
        public Time createFromParcel(Parcel in) {
            return new Time(in);
        }

        @Override
        public Time[] newArray(int size) {
            return new Time[size];
        }
    };

    public Date getPreetyTime() {
        return preetyTime;
    }



    Date preetyTime;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(preetyTime);
    }
}
