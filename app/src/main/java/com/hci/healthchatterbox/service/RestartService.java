package com.hci.healthchatterbox.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by w on 2018-05-18.
 */

public class RestartService extends BroadcastReceiver {
    public static final String ACTION_RESTART_ALARMSERVICE = "ACTION.Restart.AlarmService";
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("000 RestartService" , "RestartService called : " + intent.getAction());

        /**
         * 서비스 죽일때 알람으로 다시 서비스 등록
         */
        /*if(intent.getAction().equals(ACTION_RESTART_ALARMSERVICE)){

            Log.i("000 RestartService" ,"ACTION.RESTART.AlarmService " );

            Intent i = new Intent(context,AlarmService.class);
            context.startService(i);
        }*/

        /**
         * 폰 재시작 할때 서비스 등록
         */
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){

            Log.i("RestartService" , "ACTION_BOOT_COMPLETED" );
            Intent i = new Intent(context,AlarmService.class);
            context.startService(i);

        }


    }
}