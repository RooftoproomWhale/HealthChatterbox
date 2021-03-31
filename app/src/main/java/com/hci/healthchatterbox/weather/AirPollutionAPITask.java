package com.hci.healthchatterbox.weather;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by w on 2018-04-27.
 */

public class AirPollutionAPITask extends AsyncTask<Double, Void, AirPollution> {

    private static final String TAG = "AirPollutionAPI";

    @Override
    protected AirPollution doInBackground(Double... params) {
        AirPollutionAPIClient airPollutionAPIClient = new AirPollutionAPIClient();

        double lat = params[0];
        double lon = params[1];

        AirPollution a = airPollutionAPIClient.getAirPollution(lat,lon);

        Log.i(TAG,this.isCancelled()+"");

        return a;
    }
}
