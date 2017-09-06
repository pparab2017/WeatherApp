package com.example.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CityWeather extends AppCompatActivity implements WeatherGetAsync.GetWeather {

    ListView weatherList;
    String[] stateCityName;
    ArrayList<Weather> weatherArrayList;
    final static String SIGLE_WEATHER = "singleWeather";
    final static String API_KEY = "e65b59a79798e3ba";
    final String KEY ="{KEY}",STATE ="{STATE}", CITY ="{CITY}";
    final String BASE_URL = "http://api.wunderground.com/api/{KEY}/hourly/q/{STATE}/{CITY}.json";
    TextView currentLocation;
    long defaultTimer = 5000;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {

            case R.id.itemAddToFav:


                SharedPreferences mPrefs = getSharedPreferences(MainActivity.FAV_CITY_WEATHER,MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();

                Toast updateMessage = null;
                Gson gson = new Gson();

                DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd hhmmss");
                dateFormatter.setLenient(false);
                Date today = new Date();
                String s = dateFormatter.format(today);

                FavoriteCityWeather toAdd = new FavoriteCityWeather();
                toAdd.setTemperature(weatherArrayList.get(0).getTemperature());
                toAdd.setCurrentTime(today);
                toAdd.setCity(stateCityName[1]);
                toAdd.setState(stateCityName[0]);

                if(mPrefs.contains(toAdd.getKey()))
                {
                     prefsEditor.remove(toAdd.getKey());
                     updateMessage = Toast.makeText(this, getResources().getText(R.string.UpdatedFavoritesRecordTag), Toast.LENGTH_SHORT);
                }else
                {
                    updateMessage  = Toast.makeText(this, getResources().getText(R.string.AddedtoFavoritesTag), Toast.LENGTH_SHORT);
                }

                String json = gson.toJson(toAdd);
                prefsEditor.putString(toAdd.getKey(), json);

                prefsEditor.commit();

                updateMessage.show();



                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater myMenu = getMenuInflater();
        myMenu.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        currentLocation = (TextView)findViewById(R.id.textViewLocation);
        weatherList = (ListView) findViewById(R.id.listViewWeather);
        weatherList.setDivider(null);
        weatherList.setDividerHeight(10);


        if(getIntent().getExtras().containsKey(MainActivity.STATE_CITY_NAME))
        {
            stateCityName = getIntent().getExtras().getStringArray(MainActivity.STATE_CITY_NAME);

            new WeatherGetAsync(this).execute(generateURL());

        }

    }

    private String generateURL()
    {
        String city = stateCityName[1];
        String state = stateCityName[0];
        HashMap<String,String> urlGenerate = new HashMap<>();

        urlGenerate.put(KEY, API_KEY);
        urlGenerate.put(STATE, state);
        urlGenerate.put(CITY, city);

        String finalUrl ="";
        String thisBaseUrl = BASE_URL;
        for(HashMap.Entry<String, String> val : urlGenerate.entrySet())
        {
            finalUrl = thisBaseUrl.replace(val.getKey(), val.getValue());
            thisBaseUrl = finalUrl;
        }
        return finalUrl;
    }

    @Override
    public void putOnMainActivity(ArrayList<Weather> weathers) {


        if (!weathers.get(0).isError) {

            currentLocation.setText((stateCityName[1].contains("_") ?
                    (FavoriteCityWeather.titleCase(stateCityName[1].replace("_"," "))) :
                    (FavoriteCityWeather.titleCase(stateCityName[1])) )+", " + (stateCityName[0].toUpperCase()) );
            weatherArrayList = weathers;
            WeatherAdapter adapter = new WeatherAdapter(this, R.layout.weather_item_row, weatherArrayList);
            adapter.setNotifyOnChange(true);
            weatherList.setAdapter(adapter);
            weatherList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(CityWeather.this, WeatherDetail.class);
                    weatherArrayList.get(position).setMaxTemperature(weatherArrayList);
                    weatherArrayList.get(position).setMinTemperature(weatherArrayList);
                    i.putExtra(SIGLE_WEATHER, weatherArrayList.get(position));
                    i.putExtra(MainActivity.STATE_CITY_NAME, stateCityName);
                    startActivity(i);

                }
            });

        }
        else
        {
            Toast.makeText(CityWeather.this,weathers.get(0).error.getErrorString(),Toast.LENGTH_LONG).show();

            CountDownTimer timer = new CountDownTimer(defaultTimer, 1000) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
                    cancel();
                    Intent toSend = new Intent();
                    setResult(RESULT_OK,toSend);
                    finish();
                }
            }.start();



        }
    }

    @Override
    public Context getContext() {
        return this;
    }


}
