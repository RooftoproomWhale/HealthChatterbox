package com.hci.healthchatterbox.weather;

import com.hci.healthchatterbox.R;

/**
 * Created by w on 2018-04-27.
 */

public class AirPollution {
    double lat;
    double ion;
    int AirPol;
    String sAirpol;
    int AirImage;

    public void setAirPol(int airPol) {
        AirPol = airPol;
    }
    public void setIon(double ion) {
        this.ion = ion;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getIon() {
        return ion;
    }
    public double getLat() {
        return lat;
    }
    public int getAirPol() {
        return AirPol;
    }
    public int getAirImage()
    {
        if(AirPol < 51)
        {
            AirImage = R.drawable.airpollution1;
        }
        else if(AirPol < 101)
        {
            AirImage = R.drawable.airpollution2;
        }
        else if(AirPol < 201)
        {
            AirImage = R.drawable.airpollution3;
        }
        else if(AirPol > 200)
        {
            AirImage = R.drawable.airpollution4;
        }
        return AirImage;
    }
    public String getAirPolText()
    {
        if(AirPol < 51)
        {
            sAirpol = "좋음";
        }
        else if(AirPol < 101)
        {
            sAirpol = "보통";
        }
        else if(AirPol < 201)
        {
            sAirpol = "나쁨";
        }
        else if(AirPol < 301)
        {
            sAirpol = "매우나쁨";
        }
        else if(AirPol > 300)
        {
            sAirpol = "위험";
        }
        return sAirpol;
    }

}
