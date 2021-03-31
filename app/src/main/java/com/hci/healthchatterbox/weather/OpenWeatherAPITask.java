package com.hci.healthchatterbox.weather;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by w on 2018-04-27.
 */

public class OpenWeatherAPITask extends AsyncTask<Double, Void, Weather> {

    private static final String TAG = "OpenWeatherAPI";

    @Override
    protected Weather doInBackground(Double... params) {
        OpenWeatherAPIClient client = new OpenWeatherAPIClient();

        double lat = params[0];
        double lon = params[1];

        Weather w = client.getWeather(lat,lon);

        Log.i(TAG,this.isCancelled()+"");

        return w;
    }
}

