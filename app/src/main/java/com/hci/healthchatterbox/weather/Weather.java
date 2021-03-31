package com.hci.healthchatterbox.weather;

import com.hci.healthchatterbox.R;

/**
 * Created by w on 2018-04-27.
 */

public class Weather {
    double lat;
    double ion;
    int temprature;
    String cloudy;
    String koCloudy;
    String city;
    int cloudyImage;

    public void setLat(double lat){ this.lat = lat;}
    public void setIon(double ion){ this.ion = ion;}
    public void setTemprature(int t){ this.temprature = t;}
    public void setCloudy(String cloudy){ this.cloudy = cloudy;}
    public void setCity(String city){ this.city = city;}

    public double getLat(){ return lat;}
    public double getIon() { return ion;}
    public int getTemprature() { return temprature;}
    public String getCloudy()
    {
        if(cloudy.equals("Clouds"))
        {
            koCloudy = "흐림";
        }
        else if(cloudy.equals("Rain")||cloudy.equals("Drizzle"))
        {
            koCloudy = "비";
        }
        else if(cloudy.equals("Snow"))
        {
            koCloudy = "눈";
        }
        else if(cloudy.equals("Clear"))
        {
            koCloudy = "맑음";
        }
        else if(cloudy.equals("Mist"))
        {
            koCloudy = "안개";
        }
        return koCloudy;
    }

    public int getCloudyImage() {
        if(cloudy.equals("Clouds") || cloudy.equals("Mist"))
        {
            cloudyImage = R.drawable.weather3;
        }
        else if(cloudy.equals("Rain")||cloudy.equals("Drizzle"))
        {
            cloudyImage = R.drawable.weather4;
        }
        else if(cloudy.equals("Snow"))
        {
            cloudyImage = R.drawable.weather7;
        }
        else if(cloudy.equals("Clear"))
        {
            cloudyImage = R.drawable.weather1;
        }
        return cloudyImage;
    }

    public String getCity() { return city; }
}
