package com.hci.healthchatterbox.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by w on 2018-05-02.
 */

public class PushAlarm {
    private Context context;
    public PushAlarm(Context context) {
        this.context = context;
    }

    public void Alarm()
    {
        long oneday = 24 * 60 * 60 * 1000;// 24시간
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context,BroadcastD.class);
        PendingIntent sender = PendingIntent.getBroadcast(context,1,intent,0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 8, 0, 0);
        int diff = (int)(Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis());

        System.out.println("Alarm : "+diff);

            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), oneday, sender);



        intent = new Intent(context,BroadcastD.class);
        sender = PendingIntent.getBroadcast(context,2,intent,0);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 13, 0, 0);
        diff = (int)(Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis());

            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), oneday, sender);


        intent = new Intent(context,BroadcastD.class);
        sender = PendingIntent.getBroadcast(context,3,intent,0);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 19, 0, 0);
        diff = (int)(Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis());


            am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),oneday,sender);

//        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 8, 0, 0);
//        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 13, 0, 0);
//        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 19, 0, 0);
    }
    public void unregisterAlarm()
    {

    }
}
