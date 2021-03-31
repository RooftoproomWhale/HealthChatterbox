package com.hci.healthchatterbox.weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by w on 2018-04-27.
 */

public class OpenWeatherAPIClient {
    final static String openWeatherURL = "http://api.openweathermap.org/data/2.5/weather";
    public Weather getWeather(double lat, double lon)
    {
        Weather w = new Weather();
        String urlString = openWeatherURL + "?lat="+lat+"&lon="+lon +"&APPID=6a0d759a3991ae2b9493415ead600ea0&units=metric";
        try
        {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setConnectTimeout(6000);
            urlConnection.setReadTimeout(6000);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            JSONObject json = new JSONObject(getStringFromInputStream(in));

            // parse JSON

            w = parseJSON(json);
            w.setIon(lon);
            w.setLat(lat);
        }
        catch (MalformedURLException e)
        {
            System.err.println("Malformed URL");
            e.printStackTrace();
            return null;
        }
        catch (JSONException e)
        {
            System.err.println("JSON parsing error");
            e.printStackTrace();
            return null;
        }
        catch (IOException e)
        {
            System.err.println("URL Connection failed");
            e.printStackTrace();
            return null;
        }
        return w;
    }

    private Weather parseJSON(JSONObject json) throws JSONException {

        Weather w = new Weather();

        w.setTemprature(json.getJSONObject("main").getInt("temp"));

        w.setCity(json.getString("name"));

        w.setCloudy(json.getJSONArray("weather").getJSONObject(0).getString("main"));

        return w;

    }

    private static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;

        StringBuilder sb = new StringBuilder();

        String line;

        try {
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
