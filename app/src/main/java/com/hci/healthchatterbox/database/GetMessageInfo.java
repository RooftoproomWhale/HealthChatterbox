package com.hci.healthchatterbox.database;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by w on 2018-04-30.
 */

public class GetMessageInfo extends AsyncTask<String, Void, String>{
    private static final String TAG = "GetMessageInfo";
    public static String ip ="203.241.228.231:8080";
    String sendMsg, receiveMsg;
    String serverip = "http://"+ip+"/Message_server/data.jsp";
    Context context;
    ProgressDialog dialog;

    public GetMessageInfo(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        try
        {
            String str;
            URL url = new URL(serverip);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            sendMsg = "sex="+strings[0]+"&ages="+strings[1]+"&obesity="+strings[2]+"&health="+strings[3]+
                    "&mentality="+strings[4]+"&sleep="+strings[5]+"&weather="+strings[6]+"&dust="+strings[7]+"&nutrition="+strings[8];
            osw.write(sendMsg);
            osw.flush();
            System.out.println("통신"+conn.getResponseCode());
            if(conn.getResponseCode() == conn.HTTP_OK)
            {
                Log.i("통신 결과", conn.getResponseCode()+"성공");
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();

                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
            }
            else
            {
                Log.i("통신 결과", conn.getResponseCode()+"에러");
            }
            Log.i(TAG,"doInBackground-Done");
            Log.i(TAG,this.isCancelled()+"");
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return receiveMsg;
    }

    @Override
    protected void onPreExecute() {
        Log.i(TAG,"PreExecute");
        super.onPreExecute();
        /*dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.show();*/
    }

    @Override
    protected void onPostExecute(String s) {
        Log.i(TAG,"PostExecute");
        super.onPostExecute(s);
        if(dialog.isShowing())
        {
            dialog.dismiss();
        }
    }
}
