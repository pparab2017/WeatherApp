package com.example.weatherapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by pushparajparab on 10/4/16.
 */
public class Weather  implements Parcelable{
        Time time;
        Unit temperature;
        Unit dewpoint;
        String clowds;
        String iconURL;
        Unit windSpeed;
        Unit windDirection;
        String climateType;
        Unit feelslike;
        Unit pressure;
        String humidity;
    Error error;

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    boolean isError;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }



    public int getMaxTemp() {
        return maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }




    int maxTemp;
    int minTemp;

    protected Weather(Parcel in) {
        time = in.readParcelable(Time.class.getClassLoader());
        temperature = in.readParcelable(Unit.class.getClassLoader());
        dewpoint = in.readParcelable(Unit.class.getClassLoader());
        clowds = in.readString();
        iconURL = in.readString();
        windSpeed = in.readParcelable(Unit.class.getClassLoader());
        windDirection = in.readParcelable(Unit.class.getClassLoader());
        climateType = in.readString();
        feelslike = in.readParcelable(Unit.class.getClassLoader());
        pressure = in.readParcelable(Unit.class.getClassLoader());
        humidity = in.readString();
        maxTemp = in.readInt();
        minTemp = in.readInt();
        error = in.readParcelable(Error.class.getClassLoader());
        isError = (boolean)in.readValue(boolean.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(time, flags);
        dest.writeParcelable(temperature, flags);
        dest.writeParcelable(dewpoint, flags);
        dest.writeString(clowds);
        dest.writeString(iconURL);
        dest.writeParcelable(windSpeed, flags);
        dest.writeParcelable(windDirection, flags);
        dest.writeString(climateType);
        dest.writeParcelable(feelslike, flags);
        dest.writeParcelable(pressure, flags);
        dest.writeString(humidity);
        dest.writeInt(maxTemp);
        dest.writeInt(minTemp);
        dest.writeParcelable(error,flags);
        dest.writeValue(isError);
    }

    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

    public Unit getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Unit windDirection) {
        this.windDirection = windDirection;
    }



    public Weather(){}

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Unit getTemperature() {
        return temperature;
    }

    public void setTemperature(Unit temperature) {
        this.temperature = temperature;
    }

    public Unit getDewpoint() {
        return dewpoint;
    }

    public void setDewpoint(Unit dewpoint) {
        this.dewpoint = dewpoint;
    }

    public String getClowds() {
        return clowds;
    }

    public void setClowds(String clowds) {
        this.clowds = clowds;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public Unit getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Unit windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getClimateType() {
        return climateType;
    }

    public void setClimateType(String climateType) {
        this.climateType = climateType;
    }

    public Unit getFeelslike() {
        return feelslike;
    }

    public void setFeelslike(Unit feelslike) {
        this.feelslike = feelslike;
    }

    public Unit getPressure() {
        return pressure;
    }

    public void setPressure(Unit pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setMaxTemperature(ArrayList<Weather> hourly)
    {
        int max  = Integer.parseInt(hourly.get(0).getTemperature().english);
        for (int i = 1;i< hourly.size();i++) {
            int tocompare = Integer.parseInt( hourly.get(i).getTemperature().english);
            if(tocompare > max )
            {
                max = tocompare;
            }
        }
        this.maxTemp =  max;

    }

    public void setMinTemperature(ArrayList<Weather> hourly)
    {

        int min  = Integer.parseInt(hourly.get(0).getTemperature().english);
        for (int i = 1;i< hourly.size();i++) {
            int tocompare = Integer.parseInt( hourly.get(i).getTemperature().english);
            if(tocompare < min )
            {
                min = tocompare;
            }
        }
        this.minTemp =  min;
    }



    @Override
    public int describeContents() {
        return 0;
    }


}
