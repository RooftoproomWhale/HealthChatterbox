package com.hci.healthchatterbox.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Created by w on 2018-05-17.
 */

public class MessageRenewal {
    private static final String TAG = "MainActivity";
    private GetMessageInfo task;
    private String result;
    private InnerDataBase indb;
    private Context context;
    private int intRandom;
    private String message;
    private Random rand = new Random();

    public MessageRenewal(Context context)
    {
        this.context = context;
    }

    public void setMessage(String obesity, String health , String mentality, String sleep , String weather , String dust , String nutrition)
    {
        indb = new InnerDataBase(context,"message.db");

        Log.i(TAG,"gender : "+indb.getResult("select sex from messagetable")+" ages : "+indb.getResult("select ages from messagetable"));
        try {
            task = new GetMessageInfo(context);
            result = task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,indb.getResult("select sex from messagetable"),indb.getResult("select ages from messagetable"),obesity,health,mentality,sleep,weather,dust,nutrition).get();
            if (task.getStatus() == GetMessageInfo.Status.RUNNING) {
                Log.i(TAG,"getStatus");
                task.cancel(true);
            }
            Log.i(TAG,result);

            String str1[] = result.split("<body>");
            String str2[] = str1[1].split("</body>");
            String str3[] = str2[0].split("\\/");

            Log.i(TAG,""+str3.length);

            indb.close();

            indb = new InnerDataBase(context,"datastorage.db");

            if(indb.getResult("select messageindex from datastoragetable").equals("null"))
            {
                intRandom = rand.nextInt(str3.length);
                indb.insert(InDBAdapter.updateMessageIndexQuery(intRandom));
                indb.insert(InDBAdapter.updateMessageMaxIndexQuery(str3.length));
                indb.insert(InDBAdapter.updateMessageTextQuery(str3[intRandom]));
                message = str3[intRandom];
            }
            else
            {
                intRandom = Integer.parseInt(indb.getResult("select messageindex from datastoragetable"));
                message = indb.getResult("select messagetext from datastoragetable");
            }
            indb.close();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void Renewal(String obesity, String health , String mentality, String sleep , String weather , String dust , String nutrition)
    {
        indb = new InnerDataBase(context,"message.db");

        Log.i(TAG,"gender : "+indb.getResult("select sex from messagetable")+" ages : "+indb.getResult("select ages from messagetable"));
        try {
            task = new GetMessageInfo(context);
            result = task.execute(indb.getResult("select sex from messagetable"),indb.getResult("select ages from messagetable"),obesity,health,mentality,sleep,weather,dust,nutrition).get();
            if (task.getStatus() == GetMessageInfo.Status.RUNNING) {
                Log.i(TAG,"getStatus");
                task.cancel(true);
            }
            Log.i(TAG,result);

            String str1[] = result.split("<body>");
            String str2[] = str1[1].split("</body>");
            String str3[] = str2[0].split("\\/");

            Log.i(TAG,""+str3.length);

            indb.close();

            indb = new InnerDataBase(context,"datastorage.db");

            intRandom = rand.nextInt(str3.length);
            indb.insert(InDBAdapter.updateMessageIndexQuery(intRandom));
            indb.insert(InDBAdapter.updateMessageMaxIndexQuery(str3.length));
            indb.insert(InDBAdapter.updateMessageTextQuery(str3[intRandom]));
            message = str3[intRandom];

            indb.close();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public String getMessage()
    {
        return message;
    }
}
