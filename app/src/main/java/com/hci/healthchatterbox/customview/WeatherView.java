package com.hci.healthchatterbox.customview;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hci.healthchatterbox.R;

/**
 * Created by w on 2018-05-01.
 */

public class WeatherView extends LinearLayout {
    TextView WeatherText;
    ImageView WeatherImage;

    public WeatherView(Context context)
    {
        super(context);
        initView();
    }

    public WeatherView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        initView();
        getAttrs(attrs);
    }

    public WeatherView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context,attrs);
        initView();
        getAttrs(attrs,defStyle);
    }

    private void initView()
    {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater)getContext().getSystemService(infService);
        View v = li.inflate(R.layout.weather_view,this,false);
        addView(v);

        WeatherText = (TextView)findViewById(R.id.weathertext);
        WeatherImage = (ImageView)findViewById(R.id.weatherimage);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.WeatherImage);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.WeatherImage, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {
        String text = typedArray.getString(R.styleable.WeatherImage_Text);
        WeatherText.setText(text);

        int image = typedArray.getResourceId(R.styleable.WeatherImage_Imagesrc,R.drawable.weather1);
        WeatherImage.setImageResource(image);

        typedArray.recycle();
    }

    public void setWeatherImage(int weatherImage) {
        WeatherImage.setImageResource(weatherImage);
    }

    public void setWeatherText(String weatherText) {
        WeatherText.setText(weatherText);
    }
}
