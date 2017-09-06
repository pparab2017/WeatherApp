package com.example.weatherapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    final static String WEATHER_LIST = "weatherList";
    final static String STATE_CITY_NAME = "stateAndCityName";
    final static int STATE_CITY_NAME_CODE = 00101;
    final static String FAV_CITY_WEATHER = "FavoriteCityWeather";
    ArrayList<FavoriteCityWeather> favWeatherArrayList;
    ArrayList<FavoriteCityWeather> favCity;
    ListView CityList;

    Button binSubmit;
    EditText textCity,textState;
    TextView textViewFav;


    @Override
    protected void onPostResume() {
        super.onPostResume();
        MakeFavList();
    }

    private void MakeFavList()
    {
        try {

            SharedPreferences mPrefs = getSharedPreferences(MainActivity.FAV_CITY_WEATHER,MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();

            Gson gson = new Gson();
            HashMap<String, String> json = (HashMap<String, String>) mPrefs.getAll();

            favCity  = new ArrayList<FavoriteCityWeather>();

            for (Map.Entry<String, String> entry : json.entrySet()) {
                String key = entry.getKey();
                String  value = entry.getValue();
                FavoriteCityWeather obj = gson.fromJson(value, FavoriteCityWeather.class);
                favCity.add(obj);
            }

            CityList = (ListView)findViewById(R.id.listViewFavCities);
            textViewFav = (TextView)findViewById(R.id.textViewTagFav);
            CityList.setDivider(null);
            CityList.setDividerHeight(10);
            if(favCity.size() != 0)
            {
                FavoriteCityAdapter adapter = new FavoriteCityAdapter(this,R.layout.favorite_city_item_row,favCity);
                adapter.setNotifyOnChange(true);
                CityList.setAdapter(adapter);
            }

            updateTextTag();

            CityList.setOnItemLongClickListener(new OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    SharedPreferences mPrefs = getSharedPreferences(MainActivity.FAV_CITY_WEATHER,MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    FavoriteCityWeather favCityWeather =  favCity.get(position);
                    prefsEditor.remove(favCityWeather.getKey());
                    favCity.remove(position);
                    prefsEditor.commit();
                    FavoriteCityAdapter adapter = new FavoriteCityAdapter(MainActivity.this,R.layout.favorite_city_item_row,favCity);
                    adapter.setNotifyOnChange(true);
                    CityList.setAdapter(adapter);
                    updateTextTag();
                    Toast.makeText(MainActivity.this,"City Deleted" ,Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }
        catch (Exception ex)
        {
            throw  ex;
        }
    }

    private void updateTextTag()
    {
        if( CityList.getCount() == 0)
        {textViewFav.setText(getResources().getText(R.string.errorNoCityInFav));}
        else
        {   textViewFav.setText("Favorites");}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetTheAppIcon();
        textCity = (EditText) findViewById(R.id.editTextCity);
        textState = (EditText) findViewById(R.id.editTextState);
        binSubmit = (Button) findViewById(R.id.buttonSubmit);
        binSubmit.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null) {
            switch (requestCode) {
                case STATE_CITY_NAME_CODE:
                break;
            }
        }

    }

    private void SetTheAppIcon()
    {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }



    @Override
    public void onClick(View v) {
        String city = textCity.getText().toString().trim();
        String state = textState.getText().toString().trim();
        if(!city.isEmpty() && !state.isEmpty()) {
            city = city.contains(" ") ? city.replace(" ", "_") : city;
            Intent detailWeather = new Intent(MainActivity.this, CityWeather.class);
            detailWeather.putExtra(STATE_CITY_NAME, new String[]{state, city});
            startActivityForResult(detailWeather, STATE_CITY_NAME_CODE);
            textCity.setText("");
            textState.setText("");
        }
        else
        {
            Toast.makeText(MainActivity.this, getResources().getText(R.string.errorNoCityState),Toast.LENGTH_LONG).show();
        }

    }
}
