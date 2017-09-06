package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by pushparajparab on 10/5/16.
 */
public class WeatherAdapter extends ArrayAdapter<Weather> {
    List<Weather> mWeatherList;
    Context mContext;
    int mResource;




    public WeatherAdapter(Context context, int resource, List<Weather> objects) {
        super(context, resource, objects);
        this.mWeatherList = objects;
        this.mContext = context;
        this.mResource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Weather singleWeather = this.mWeatherList.get(position);
        ViewHolder holder;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
            holder = new ViewHolder();
            holder.imageViewIconHolder = (ImageView) convertView.findViewById(R.id.imageViewIcon);
            holder.textViewTimeHolder = (TextView) convertView.findViewById(R.id.textViewTime);
            holder.textViewTempHolder = (TextView) convertView.findViewById(R.id.textViewTemp);
            holder.textViewConditionHolder = (TextView) convertView.findViewById(R.id.textViewCondition);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        TextView textViewTime = holder.textViewTimeHolder;
        TextView textViewTemp = holder.textViewTempHolder;
        TextView textViewCondition = holder.textViewConditionHolder;
        ImageView imageViewIcon = holder.imageViewIconHolder;


        //Set details
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a", Locale.US);
        String dateString = format.format(singleWeather.time.getPreetyTime());
        textViewTime.setText(dateString);
        textViewTemp.setText(singleWeather.temperature.getEnglish() + " °F");
        textViewCondition.setText(singleWeather.clowds.toString());



        Picasso.with(mContext).load(singleWeather.getIconURL()).placeholder(R.drawable.loading).into(imageViewIcon);

        return convertView;
    }


    static class ViewHolder
    {
        ImageView imageViewIconHolder;
        TextView textViewTimeHolder;
        TextView textViewConditionHolder;
        TextView textViewTempHolder;

    }
}


