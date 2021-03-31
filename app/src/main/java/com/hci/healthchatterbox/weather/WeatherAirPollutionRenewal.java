package com.hci.healthchatterbox.weather;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/**
 * Created by w on 2018-05-15.
 */

public class WeatherAirPollutionRenewal {

    private static final String TAG = "WeatherAirPollution";
    private Context context;
    private GpsInfo gps;
    private double latitude, longitude;
    private Weather w;
    private AirPollution a;

    public WeatherAirPollutionRenewal(Context context)
    {
        this.context = context;
        gps = new GpsInfo(context);

        if (gps.isGetLocation()) {
            double la = gps.getLatitude();
            double lo = gps.getLongitude();
            latitude = (Math.round(la*100d) / 100d);
            longitude = (Math.round(lo*100d) / 100d);

            OpenWeatherAPITask t = new OpenWeatherAPITask();
            try
            {
                //w = t.execute(latitude, longitude).get();
                w = t.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,latitude,longitude).get();
                if (t.getStatus() == OpenWeatherAPITask.Status.RUNNING) {
                    t.cancel(true);
                }
                Log.i(TAG,t.isCancelled()+"");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (ExecutionException e) {
                e.printStackTrace();
            }

            AirPollutionAPITask at = new AirPollutionAPITask();
            try
            {
                //a = at.execute(latitude,longitude).get();
                a = at.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,latitude,longitude).get();
                Log.i(TAG,"AirPollution = "+a);
                if(a == null && a.equals(""))
                {
                    Log.i(TAG,"AirPollution = null");
                    at.cancel(true);
                    at.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,latitude,longitude).get();
                }
                if (at.getStatus() == AirPollutionAPITask.Status.RUNNING) {
                    at.cancel(true);
                }
                Log.i(TAG,at.isCancelled()+"");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (ExecutionException e) {
                e.printStackTrace();
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }

            System.out.println("MainActivity : "+String.valueOf(latitude)+", "+String.valueOf(longitude)+", "+w.getCity()+", "+w.getTemprature()+", "+w.getCloudy()+", "+a.getAirPol());


        } else {
            // GPS 를 사용할수 없으므로
            gps.showSettingsAlert();
        }
    }

    public int getCloudyImage()
    {
        return w.getCloudyImage();
    }

    public String getCloudy()
    {
        return w.getCloudy();
    }

    public int getAirImage()
    {
        return a.getAirImage();
    }

    public String getAirPolText()
    {
        return a.getAirPolText();
    }
    public String getTemperature()
    {
        return (w.getTemprature())+"'C";
    }
}
