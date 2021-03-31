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
import com.hci.healthchatterbox.weather.AirPollution;

/**
 * Created by w on 2018-05-01.
 */

public class AirPollutionView extends LinearLayout {
    TextView text1;
    ImageView img;

    public AirPollutionView(Context context)
    {
        super(context);
        initView();
    }

    public AirPollutionView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        initView();
        getAttrs(attrs);
    }

    public AirPollutionView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context,attrs);
        initView();
        getAttrs(attrs,defStyle);
    }

    private void initView()
    {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater)getContext().getSystemService(infService);
        View v = li.inflate(R.layout.airpollution_view,this,false);
        addView(v);

        text1 = (TextView)findViewById(R.id.AirPollutionText1);
        img = (ImageView)findViewById(R.id.AirPollutionImage);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.AirPollutionImage);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.AirPollutionImage, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {
        String text = typedArray.getString(R.styleable.AirPollutionImage_Text1);
        this.text1.setText(text);

        int img = typedArray.getResourceId(R.styleable.AirPollutionImage_AirImagesrc,R.drawable.airpollution1);
        this.img.setImageResource(img);

        typedArray.recycle();
    }

    public void setAirPollutionText(String Text) {
        text1.setText(Text);
    }

    public void setAirPollutionImage(int image) {
        img.setImageResource(image);
    }
}
