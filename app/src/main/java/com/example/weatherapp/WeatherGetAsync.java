package com.example.weatherapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by pushparajparab on 10/4/16.
 */
public class WeatherGetAsync extends AsyncTask<String,Void,ArrayList<Weather>> {

    GetWeather weather;
    ProgressDialog progressDialog;

    public  WeatherGetAsync(GetWeather myWeather)
    {
        this.weather = myWeather;
    }

    interface GetWeather
    {
        public void putOnMainActivity(ArrayList<Weather> weathers);
        public Context getContext();
    }

    @Override
    protected void onPreExecute() {
        //ProgressDialog progressDialog = new ProgressDialog()
        super.onPreExecute();
        Context inNeed = weather.getContext();
        progressDialog = new ProgressDialog(inNeed);
        progressDialog.setMessage("Loading Hourly Data...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<Weather> weathers) {
        this.weather.putOnMainActivity(weathers);
        progressDialog.dismiss();
        super.onPostExecute(weathers);
    }

    @Override
    protected ArrayList<Weather> doInBackground(String... params) {
        URL url = null;
        ArrayList<Weather> toShow = null;
        BufferedReader reader = null;
        try {
             url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int statusCode = connection.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK)
            {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line = reader.readLine();
               // line = reader.readLine();

                while (line!= null)
                {
                    try{
                        stringBuilder.append(line + "\n");
                    line = reader.readLine();
                }
                    catch (Exception e)
                    {

                    }
                }
                return ParseWeatherJson.ParseWithTheString.Parse(stringBuilder.toString());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return null;
    }
}

