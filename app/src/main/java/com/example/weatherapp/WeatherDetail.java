package com.example.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class WeatherDetail extends AppCompatActivity {

    Weather weather;
    TextView maxTemp,minTemp,humidity,temp,condition,feelsLike,dewpoint,winds,clouds,pressure;
    ImageView imgIcon;
    TextView currentLocation;
    String[] stateCityName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        maxTemp = (TextView)findViewById(R.id.textViewTempMax);
        minTemp = (TextView)findViewById(R.id.textViewTempMin);
        humidity= (TextView)findViewById(R.id.textViewHumidity);
        temp= (TextView)findViewById(R.id.textViewTemp);
        condition= (TextView)findViewById(R.id.textViewCondition);
        feelsLike= (TextView)findViewById(R.id.textViewFeelsLike);
        dewpoint= (TextView)findViewById(R.id.textViewDewpoint);
        winds= (TextView)findViewById(R.id.textViewWind);
        clouds= (TextView)findViewById(R.id.textViewClouds);
        pressure= (TextView)findViewById(R.id.textViewPressure);
        imgIcon = (ImageView)findViewById(R.id.imageViewMain);
        currentLocation = (TextView)findViewById(R.id.textViewCurrentLocation);

        if(getIntent().getExtras().containsKey(MainActivity.STATE_CITY_NAME))
        {
            stateCityName = getIntent().getExtras().getStringArray(MainActivity.STATE_CITY_NAME);



        }

        if (getIntent().getExtras().containsKey(CityWeather.SIGLE_WEATHER)) {
            weather = getIntent().getExtras().getParcelable(CityWeather.SIGLE_WEATHER);
            SimpleDateFormat format = new SimpleDateFormat("hh:mm a", Locale.US);
            String dateString = format.format(weather.time.getPreetyTime());

            currentLocation.setText((stateCityName[1].contains("_") ?
                    (FavoriteCityWeather.titleCase(stateCityName[1].replace("_"," "))) :
                    (FavoriteCityWeather.titleCase(stateCityName[1])) )+", " + (stateCityName[0].toUpperCase()) );

            temp.setText(weather.getTemperature().getEnglish() + " °F");
            maxTemp.setText( "Max Temperature: " + weather.getMaxTemp() + " Fahrenheit");
            minTemp.setText( "Min Temperature: " + weather.getMinTemp() + " Fahrenheit");
            condition.setText(weather.getClowds().toString());

            feelsLike.setText(weather.getFeelslike().getEnglish().toString() + " Fahrenheit");
            humidity.setText(weather.getHumidity().toString() + " %");
            dewpoint.setText(weather.getDewpoint().getEnglish().toString() + " Fahrenheit");
            pressure.setText(weather.getPressure().getEnglish().toString() + " hPa") ;

            clouds.setText(weather.getClowds().toString());
            winds.setText(weather.getWindSpeed().getEnglish().toString() + "mph, " +
                            weather.getWindDirection().getMetric().toString() + "° " +
                    weather.getWindDirection().getEnglish().toString());
            Picasso.with(this).load(weather.getIconURL()).placeholder(R.drawable.loading).into(imgIcon);

        }
    }
}
