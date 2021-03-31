package com.hci.healthchatterbox.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.hci.healthchatterbox.R;
import com.hci.healthchatterbox.activity.MainActivity;
import com.hci.healthchatterbox.database.InnerDataBase;

/**
 * Created by w on 2018-05-02.
 */

public class BroadcastD extends BroadcastReceiver {
    private InnerDataBase indb;
    @Override
    public void onReceive(Context context, Intent intent) {
        //NotificationManager 안드로이드 상태바에 메세지를 던지기위한 서비스 불러오고
        indb = new InnerDataBase(context,"datastorage.db");
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher).setTicker("HETT").setWhen(System.currentTimeMillis()).setPriority(NotificationCompat.PRIORITY_HIGH)
                .setNumber(1).setContentTitle("건강 잔소리꾼 알림").setContentText(indb.getResult("select messagetext from datastoragetable"))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(indb.getResult("select messagetext from datastoragetable")))
                .setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE).setContentIntent(pendingIntent).setAutoCancel(true);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP && android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setCategory(Notification.CATEGORY_MESSAGE)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setVisibility(Notification.VISIBILITY_PUBLIC);
        }


        notificationmanager.notify(3, builder.build());
        indb.close();
    }
}
