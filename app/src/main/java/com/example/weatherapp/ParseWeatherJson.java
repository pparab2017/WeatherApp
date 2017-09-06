package com.example.weatherapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pushparajparab on 10/4/16.
 */
public class ParseWeatherJson {

    static final String ENGLISH = "english",METRIC ="metric";
    public static class ParseWithTheString
    {

       public static ArrayList<Weather> Parse(String jsonString) throws JSONException {

           ArrayList<Weather> toReturn = new ArrayList<Weather>();
           JSONObject root = new JSONObject(jsonString);
           if(root.has("hourly_forecast")) {
               JSONArray array = root.getJSONArray("hourly_forecast");


               for (int i = 0; i < array.length(); i++) {
                   Weather toAdd = new Weather();
                   JSONObject jTime = new JSONObject(array.getJSONObject(i).getString("FCTTIME"));
                   toAdd.setTime(new Time(jTime.getString("pretty")));
                   //temp
                   JSONObject jTemperature = new JSONObject(array.getJSONObject(i).getString("temp"));
                   toAdd.setTemperature(new Unit(jTemperature.getString(ENGLISH), jTemperature.getString(METRIC)));

                   //clowds
                   toAdd.setClowds(array.getJSONObject(i).getString("condition"));

                   //climateType
                   toAdd.setClimateType(array.getJSONObject(i).getString("wx"));

                   //Icon URL
                   toAdd.setIconURL(array.getJSONObject(i).getString("icon_url"));

                   //humidity
                   toAdd.setHumidity(array.getJSONObject(i).getString("humidity"));

                   //dewpoint
                   JSONObject jDewpoint = new JSONObject(array.getJSONObject(i).getString("dewpoint"));
                   toAdd.setDewpoint(new Unit(jDewpoint.getString(ENGLISH), jDewpoint.getString(METRIC)));

                   //wspd
                   JSONObject jWindSpeed = new JSONObject(array.getJSONObject(i).getString("wspd"));
                   toAdd.setWindSpeed(new Unit(jWindSpeed.getString(ENGLISH), jWindSpeed.getString(METRIC)));

                   //wdir
                   JSONObject jWindDirection = new JSONObject(array.getJSONObject(i).getString("wdir"));
                   toAdd.setWindDirection(new Unit(jWindDirection.getString("dir"), jWindDirection.getString("degrees")));


                   //feelslike
                   JSONObject jFeelslike = new JSONObject(array.getJSONObject(i).getString("feelslike"));
                   toAdd.setFeelslike(new Unit(jFeelslike.getString(ENGLISH), jFeelslike.getString(METRIC)));

                   //mslp
                   JSONObject jPressure = new JSONObject(array.getJSONObject(i).getString("mslp"));
                   toAdd.setPressure(new Unit(jPressure.getString(ENGLISH), jPressure.getString(METRIC)));


                   toReturn.add(toAdd);

               }
           }else if(jsonString.contains("results"))
           {
               Weather toAdd = new Weather();
               toAdd.isError = true;
               toAdd.error  = new Error();
               toAdd.error.setErrorObject("error");
               toAdd.error.setErrorString("Too many results!");
               toReturn.add(toAdd);

           }
           else if(jsonString.contains("error"))
           {
               Weather toAdd = new Weather();
               toAdd.isError = true;
               toAdd.error  = new Error();
               JSONObject errorObjectResponce = root.getJSONObject("response");
               JSONObject errorObject = errorObjectResponce.getJSONObject("error");
               toAdd.error.setErrorObject("error");
               toAdd.error.setErrorString(errorObject.getString("description"));
               toReturn.add(toAdd);

           }
            else
           {

           }
           return toReturn;
       }
    }
}
