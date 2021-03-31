package com.hci.healthchatterbox.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.hci.healthchatterbox.R;
import com.hci.healthchatterbox.activity.IntroActivity;
import com.hci.healthchatterbox.activity.MainActivity;
import com.hci.healthchatterbox.activity.SurveyActivity;
import com.hci.healthchatterbox.database.InDBAdapter;
import com.hci.healthchatterbox.database.InnerDataBase;
import com.hci.healthchatterbox.database.MessageRenewal;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmService extends Service{
    private MessageRenewal messageRenewal;
    private static boolean moring = false;
    private static boolean lunch = false;
    private static  boolean dinner = false;
    private InnerDataBase indb;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //unregisterRestartAlarm();
        Log.i("AlarmService","onCreate");
        super.onCreate();
        initData();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //startForeground(1,new Notification());

        /**
         * startForeground 를 사용하면 notification 을 보여주어야 하는데 없애기 위한 코드
         */
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification notification;
/*
        Intent intent1 = new Intent(getApplicationContext(),IntroActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity( getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);*/

        notification = new Notification.Builder(getApplicationContext())
                .setContentTitle("건강 잔소리꾼 알림 도우미")
                .setContentText("운동 메세지 대기 중...")
                //.setContentIntent(pendingNotificationIntent)
                .setSmallIcon(R.drawable.appicon)
                //.setPriority(Notification.PRIORITY_MIN)
                .build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("10001",
                    "건강 알림이 채널",
                    NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(channel);
        }

        startForeground(1,notification);

      /*  nm.notify(startId, notification);
        nm.cancel(startId);*/

        //stopSelf();

        return START_STICKY;//super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onLowMemory() {
        Log.i("AlarmService" , "onLowMemory" );
        /**
         * 서비스 종료 시 알람 등록을 통해 서비스 재 실행
         */
        //registerRestartAlarm();
        super.onLowMemory();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.i("AlarmService" , "onTaskRemoved" );
        /**
         * 서비스 종료 시 알람 등록을 통해 서비스 재 실행
         */
        //registerRestartAlarm();
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i("AlarmService" , "onDestroy" );
        /**
         * 서비스 종료 시 알람 등록을 통해 서비스 재 실행
         */
        //registerRestartAlarm();
    }

    /**
     * 데이터 초기화
     */
    private void initData(){
        Thread thread = new Thread(new AlarmThread());
        thread.start();
    }

    public class AlarmThread implements Runnable
    {
        private Handler handler = new Handler();
        private Date date;
        private InnerDataBase indb;
        private boolean dataExer;
        private String dataday, datahealth, datasleep, datamentality, datanutrition, datacause;

        @Override
        public void run() {
            while(true)
            {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                        date=new Date(System.currentTimeMillis());
                        String hms=format.format(date);

                        Log.i("AlarmService","run : "+ hms);

                        if(hms.equals("08:00")/*&&moring==false*/)
                        {
                            if(!isNetwork())
                            {
                                Toast.makeText(getApplicationContext(),"데이터나 와이파이가 켜지지 않아 데이터 송수신이 불가능합니다",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                registerNotification();
                                //moring = true;
                            }
                        }
                        if(hms.equals("13:00")/*&&lunch==false*/) {
                            if(!isNetwork())
                            {
                                Toast.makeText(getApplicationContext(),"데이터나 와이파이가 켜지지 않아 데이터 송수신이 불가능합니다",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                registerNotification();
                                //lunch = true;
                            }
                        }
                        if(hms.equals("19:00")/*&&dinner==false*/) {
                            if(!isNetwork())
                            {
                                Toast.makeText(getApplicationContext(),"데이터나 와이파이가 켜지지 않아 데이터 송수신이 불가능합니다",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                registerNotification();
                                //dinner = true;
                            }
                        }

                        indb = new InnerDataBase(getApplicationContext(),"datastorage.db");
                        if(hms.equals("23:00")&&((indb.getResult("select isexercise from datastoragetable")).equals("0")))
                        {
                            if(!isNetwork())
                            {
                                Toast.makeText(getApplicationContext(),"데이터나 와이파이가 켜지지 않아 데이터 송수신이 불가능합니다",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                NotificationManager notificationManager= (NotificationManager)getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
                                Intent intent1 = new Intent(getApplicationContext(),SurveyActivity.class); //인텐트 생성.

                                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                PendingIntent pendingNotificationIntent = PendingIntent.getActivity( getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);

                                builder.setSmallIcon(R.mipmap.ic_launcher).setTicker("건강 잔소리꾼 알림").setWhen(System.currentTimeMillis())
                                        .setNumber(1).setContentTitle("건강 잔소리꾼 알림")
                                        .setContentText("오늘 하루 운동을 하지 못하셨습니다. 설문 조사를 해주시길 바랍니다.").setPriority(NotificationCompat.PRIORITY_MAX)
                                        .setStyle(new NotificationCompat.BigTextStyle().bigText("운동을 하지 못하셨습니다. 설문 조사를 해주시길 바랍니다."))
                                        .setChannelId("10002")
                                        .setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE).setContentIntent(pendingNotificationIntent).setAutoCancel(true);


                                notificationManager.notify(2, builder.build());
                            }
                        }
                        indb.close();
                        indb = new InnerDataBase(getApplicationContext(),"datastorage.db");
                        if(hms.equals("23:59")
                                &&(!indb.getResult("select health from messagetable").equals("null")&&
                        !indb.getResult("select health from messagetable").equals("0"))) {

                          /*  moring = false;
                            lunch = false;
                            dinner = false;*/
                            if(indb.getResult("select isexercise from datastoragetable").equals("0"))
                            {
                                dataExer = false;
                            }
                            else if (indb.getResult("select isexercise from datastoragetable").equals("1"))
                            {
                                dataExer = true;
                            }
                            indb.insert(InDBAdapter.updateExerciseQuery(false));
                            indb.close();
                            indb = new InnerDataBase(getApplicationContext(),"message.db");


                            /*indb.close();

                            indb = new InnerDataBase(getApplicationContext(),"message.db");*/

                            SimpleDateFormat fformat = new SimpleDateFormat("MM-dd");
                            date=new Date(System.currentTimeMillis());
                            String yd = fformat.format(date);
                            dataday = yd;

                            datahealth = indb.getResult("select health from messagetable");
                            datasleep = indb.getResult("select sleep from messagetable");
                            datamentality = indb.getResult("select mentality from messagetable");
                            datanutrition = indb.getResult("select nutrition from messagetable");
                            datacause = indb.getResult("select cause from messagetable");
                            indb.insert(InDBAdapter.updateHealthQuery(0));
                            indb.insert(InDBAdapter.updateMentalityQuery(0));
                            indb.insert(InDBAdapter.updateSleepQuery(0));
                            indb.insert(InDBAdapter.updateNutritionQuery(0));
                            indb.insert(InDBAdapter.updateCauseQuery(0));
                            indb.close();

                            indb = new InnerDataBase(getApplicationContext(), "datastatistic.db");
                            indb.insert(InDBAdapter.insertDataStatisticQuery(dataExer,dataday,datahealth,datasleep,datamentality,datanutrition,datacause));
                            indb.close();
                        }
                       /* if(!isNetwork())
                        {
                            Toast.makeText(getApplicationContext(),"데이터나 와이파이가 켜지지 않아 데이터 송수신이 불가능합니다",Toast.LENGTH_SHORT).show();
                        }*/

                    }
                });
                try {
                    Thread.sleep(60*1000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isNetwork()
    {
        boolean isOnline = false;

        try {
            ConnectivityManager conMan = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo.State wifi = conMan.getNetworkInfo(1).getState();
            if(wifi == NetworkInfo.State.CONNECTED) {
                isOnline = true;
            }

            NetworkInfo.State mobile = conMan.getNetworkInfo(0).getState();
            if(mobile == NetworkInfo.State.CONNECTED) {
                isOnline = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOnline;
    }

    private void registerNotification()
    {
        messageRenewal = new MessageRenewal(getApplicationContext());
        messageRenewal.Renewal("1","1","1","1","1","1","1");

        indb = new InnerDataBase(getApplicationContext(),"datastorage.db");
        NotificationManager notificationManager= (NotificationManager)getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(getApplicationContext(),MainActivity.class); //인텐트 생성.

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity( getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setSmallIcon(R.drawable.appicon).setTicker("건강 잔소리꾼 알림").setWhen(System.currentTimeMillis())
                .setNumber(1).setContentTitle("건강 잔소리꾼 알림").setContentText(indb.getResult("select messagetext from datastoragetable")).setPriority(NotificationCompat.PRIORITY_MAX)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(indb.getResult("select messagetext from datastoragetable")))
                .setChannelId("10001")
                .setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE).setContentIntent(pendingNotificationIntent).setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("10001",
                    "건강 알림이 채널",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        indb.close();

        notificationManager.notify(10, builder.build());
    }

    /**
     * 알람 매니져에 서비스 등록
     */
    private void registerRestartAlarm(){

        Log.i("000 AlarmService" , "registerRestartAlarm" );
        Intent intent = new Intent(AlarmService.this,RestartService.class);
        intent.setAction(RestartService.ACTION_RESTART_ALARMSERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(this,0,intent,0);

        long firstTime = SystemClock.elapsedRealtime();
        firstTime += 1*1000;

        Log.i("000 AlarmService", ":"+firstTime);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        /**
         * 알람 등록
         */
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,firstTime,1*1000,sender);

    }

    /**
     * 알람 매니져에 서비스 해제
     */
    private void unregisterRestartAlarm(){

        Log.i("000 AlarmService" , "unregisterRestartAlarm" );

        Intent intent = new Intent(AlarmService.this,RestartService.class);
        intent.setAction(RestartService.ACTION_RESTART_ALARMSERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(this,0,intent,0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        /**
         * 알람 취소
         */
        alarmManager.cancel(sender);
    }

}
