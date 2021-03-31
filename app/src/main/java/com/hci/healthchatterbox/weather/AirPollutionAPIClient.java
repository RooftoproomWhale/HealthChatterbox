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

public class AirPollutionAPIClient {
    final static String airPollutionURL = "https://api.waqi.info/feed/geo:";
    public AirPollution getAirPollution(double lat, double lon)
    {
        AirPollution a = new AirPollution();
        String urlString = airPollutionURL + lat + ";" + lon + "/?token=6c7cff4ed47e76bd4f6e74c75f85cbeec82e9309";

        try
        {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setConnectTimeout(6000);
            urlConnection.setReadTimeout(6000);

            System.out.println("AirPollution : "+urlConnection.getInputStream());
            System.out.println("AirPollution : "+urlConnection.getResponseCode());

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            JSONObject json = new JSONObject(getStringFromInputStream(in));

            a = parseJSON(json);
            a.setIon(lon);
            a.setLat(lat);
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

        return a;
    }

    private AirPollution parseJSON(JSONObject json) throws JSONException {
        AirPollution a = new AirPollution();

        if(json.isNull("data"))
        {
            return a=null;
        }
        else
        {
            a.setAirPol(json.getJSONObject("data").getInt("aqi"));
        }

        return a;
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
