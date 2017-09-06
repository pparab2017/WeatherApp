package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by pushparajparab on 10/9/16.
 */
public class FavoriteCityAdapter extends ArrayAdapter<FavoriteCityWeather> {

    List<FavoriteCityWeather> mCityList;
    Context mContext;
    int mResource;

    public FavoriteCityAdapter(Context context, int resource, List<FavoriteCityWeather> objects) {
        super(context, resource, objects);

        this.mCityList = objects;
        this.mContext = context;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        FavoriteCityWeather singleCity = this.mCityList.get(position);
        ViewHolder holder;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
            holder = new ViewHolder();

            holder.textViewCityHolder = (TextView) convertView.findViewById(R.id.textViewFavCity);
            holder.textViewTempHolder = (TextView) convertView.findViewById(R.id.textViewFavCityTemp);
            holder.textViewLastUpdatedHolder = (TextView) convertView.findViewById(R.id.textViewFaveCityUpdatedOn);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        TextView textViewFavCity = holder.textViewCityHolder;
        TextView textViewFavCityTemp = holder.textViewTempHolder;
        TextView textViewFavCityUpdated = holder.textViewLastUpdatedHolder;


        SimpleDateFormat format = new SimpleDateFormat("dd/M/yy hh:mm:ss a", Locale.US);
        String dateString = format.format(  singleCity.getCurrentTime());
        textViewFavCity.setText(singleCity.getFormatedCityState());
        textViewFavCityTemp.setText(singleCity.getTemperature().getEnglish() + " °F");
        textViewFavCityUpdated.setText("Updated on: "+ dateString);

        return convertView;
    }

    static class ViewHolder
    {

        TextView textViewCityHolder;
        TextView textViewTempHolder;
        TextView textViewLastUpdatedHolder;

    }

}
