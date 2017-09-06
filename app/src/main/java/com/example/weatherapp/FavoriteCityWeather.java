package com.example.weatherapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by pushparajparab on 10/8/16.
 */
public class FavoriteCityWeather  extends Weather implements Parcelable  {

    String city, state;
    Date currentTime;
 public  FavoriteCityWeather(){}

    public String getFormatedCityState()
    {
        String city = (getCity().contains("_") ? getCity().replace("_"," ") : getCity());
        city = titleCase(city);
        String state = (getState().toUpperCase());
        return  city +", " +state;

    }


    public static String titleCase(String input)
    {
        String[] words = input.toString().split(" ");
        StringBuilder sb = new StringBuilder();
        if (words[0].length() > 0) {
            sb.append(Character.toUpperCase(words[0].charAt(0)) + words[0].subSequence(1, words[0].length()).toString().toLowerCase());
            for (int i = 1; i < words.length; i++) {
                sb.append(" ");
                sb.append(Character.toUpperCase(words[i].charAt(0)) + words[i].subSequence(1, words[i].length()).toString().toLowerCase());
            }
        }
        String titleCaseValue = sb.toString();
        return titleCaseValue;
    }

 public String getKey()
 {
     return this.city.toLowerCase()+this.getState().toLowerCase();
 }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    protected FavoriteCityWeather(Parcel in) {
        city = in.readString();
        state = in.readString();
        currentTime = (Date) in.readValue(Date.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(city);
        dest.writeString(state);
        dest.writeValue(currentTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FavoriteCityWeather> CREATOR = new Creator<FavoriteCityWeather>() {
        @Override
        public FavoriteCityWeather createFromParcel(Parcel in) {
            return new FavoriteCityWeather(in);
        }

        @Override
        public FavoriteCityWeather[] newArray(int size) {
            return new FavoriteCityWeather[size];
        }
    };
}
