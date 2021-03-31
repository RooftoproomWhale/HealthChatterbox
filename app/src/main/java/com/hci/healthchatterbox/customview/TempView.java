package com.hci.healthchatterbox.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hci.healthchatterbox.R;

/**
 * Created by w on 2018-05-01.
 */

public class TempView extends LinearLayout {
    TextView text;

    public TempView(Context context)
    {
        super(context);
        initView();
    }

    public TempView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        initView();
        getAttrs(attrs);
    }

    public TempView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context,attrs);
        initView();
        getAttrs(attrs,defStyle);
    }

    private void initView()
    {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater)getContext().getSystemService(infService);
        View v = li.inflate(R.layout.temp_view,this,false);
        addView(v);

        text = (TextView)findViewById(R.id.TempText);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TempImage);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TempImage, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {
        String text = typedArray.getString(R.styleable.TempImage_TempText);
        this.text.setText(text);

        typedArray.recycle();
    }

    public void setText(String text) {
        this.text.setText(text);
    }
}
