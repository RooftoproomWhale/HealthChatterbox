package com.hci.healthchatterbox.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hci.healthchatterbox.R;
import com.hci.healthchatterbox.customview.AirPollutionView;
import com.hci.healthchatterbox.customview.HealthCheckView;
import com.hci.healthchatterbox.customview.TempView;
import com.hci.healthchatterbox.customview.WeatherView;
import com.hci.healthchatterbox.database.InDBAdapter;
import com.hci.healthchatterbox.database.InnerDataBase;
import com.hci.healthchatterbox.database.MessageRenewal;
import com.hci.healthchatterbox.service.AlarmService;
import com.hci.healthchatterbox.service.RestartService;
import com.hci.healthchatterbox.util.RecycleUtils;
import com.hci.healthchatterbox.weather.WeatherAirPollutionRenewal;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private WeatherView weatherView;
    private AirPollutionView airPollutionView;
    private HealthCheckView healthCheckView;
    private TempView tempView;
    private TextView message;
    private ImageButton imageButton;
    private InnerDataBase indb;
    private WeatherAirPollutionRenewal weatherAirPollutionRenewal;
    private MessageRenewal messageRenewal;
    private RestartService restartService;
    private Intent intent;
    private Button btn;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private final static int AC_SV = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(getApplication());*/

        initData();

        btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNotification();
            }
        });
        indb = new InnerDataBase(getApplicationContext(), "datastorage.db");

        healthCheckView = (HealthCheckView)findViewById(R.id.Healthcheckview);

        if(indb.getResult("select isexercise from datastoragetable").equals("0"))
        {
            healthCheckView.setCheck(false);
        }
        else
        {
            healthCheckView.setCheck(true);
        }

        healthCheckView.ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(healthCheckView.ch.isChecked())
                {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("알림")
                            .setMessage("운동을 했을 시에만 체크 가능하며 체크시 변경 불가능합니다. 체크 하시겠습니까?")
                            .setCancelable(false)
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    indb.insert(InDBAdapter.updateExerciseQuery(true));
                                    Intent intent = new Intent(MainActivity.this, SurveyActivity.class);
                                    startActivityForResult(intent,AC_SV);
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    healthCheckView.ch.setChecked(false);
                                }
                            })
                            .show();
                }
                else
                {
                    healthCheckView.ch.setChecked(true);
                }
            }
        });
        indb.close();

        SimpleDateFormat format = new SimpleDateFormat("HH");
        Date date=new Date(System.currentTimeMillis());
        String hms=format.format(date);
        if(hms.equals("23"))
        {
            healthCheckView.ch.setClickable(false);
        }
        else
        {
            healthCheckView.ch.setClickable(true);
        }

        message = (TextView)findViewById(R.id.message_text);
        weatherView = (WeatherView)findViewById(R.id.Weatherview);
        airPollutionView = (AirPollutionView)findViewById(R.id.AirPollutionview);
        tempView = (TempView)findViewById(R.id.Tempview);
        imageButton = (ImageButton)findViewById(R.id.dataimageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, DataActivity.class);
                startActivity(intent);
            }
        });

        weatherAirPollutionRenewal = new WeatherAirPollutionRenewal(getApplicationContext());

        weatherView.setWeatherImage(weatherAirPollutionRenewal.getCloudyImage());
        weatherView.setWeatherText(weatherAirPollutionRenewal.getCloudy());
        airPollutionView.setAirPollutionImage(weatherAirPollutionRenewal.getAirImage());
        airPollutionView.setAirPollutionText(weatherAirPollutionRenewal.getAirPolText());
        tempView.setText(weatherAirPollutionRenewal.getTemperature());

        messageRenewal = new MessageRenewal(getApplicationContext());
        messageRenewal.setMessage("1","1","1","1","1","1","1");

        message.setText(messageRenewal.getMessage());
        setNotification();
    }

    private void setNotification()
    {
        messageRenewal = new MessageRenewal(getApplicationContext());
        messageRenewal.Renewal("1","1","1","1","1","1","1");
        message.setText(messageRenewal.getMessage());
//        NotificationManager notificationManager= (NotificationManager)getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(getApplicationContext(),MainActivity.class); //인텐트 생성.

//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity( getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        indb = new InnerDataBase(getApplicationContext(),"datastorage.db");
        builder.setSmallIcon(R.drawable.appicon).setTicker("HETT").setWhen(System.currentTimeMillis())//.setPriority(NotificationCompat.PRIORITY_HIGH)
                .setNumber(1).setContentTitle("건강 잔소리꾼 알림").setContentText(indb.getResult("select messagetext from datastoragetable"))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(indb.getResult("select messagetext from datastoragetable")))
                .setChannelId("10002")
                .setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE).setContentIntent(pendingNotificationIntent).setAutoCancel(true);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP && android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setCategory(Notification.CATEGORY_MESSAGE)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setVisibility(Notification.VISIBILITY_PUBLIC);
        }

        indb.close();

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("10002",
                    "건강 알림이",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }*/

        notificationManager.notify(3, builder.build()); // Notification send

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try
        {
            switch (requestCode)
            {
                case AC_SV:
                    if(data != null || resultCode == RESULT_OK)
                    {
                        Log.i(TAG,"ReSult_Ok");
                        /*messageRenewal.Renewal(indb.getResult("select obesity from messagetable"),
                                indb.getResult("select health from messagetable"),
                                indb.getResult("select mentality from messagetable"),
                                indb.getResult("select sleep from messagetable"),
                                indb.getResult("select weather from messagetable"),
                                indb.getResult("select dust from messagetable"),
                                indb.getResult("select nutrition from messagetable"));*/
                        messageRenewal.Renewal("1","1","1","1","1","1","1");

                        message.setText(messageRenewal.getMessage());
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i("MainActivity","onDestroy");
        //브로드 캐스트 해제

        unregisterReceiver(restartService);
    }

    /**
     * 데이터 초기화
     */
    private void initData(){
        notificationManager= (NotificationManager)getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("10002",
                    "건강 알림이",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("아침, 점심, 저녁으로 메세지가 옵니다.");
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setShowBadge(true);
            channel.setVibrationPattern(new long[]{100, 200, 100, 200});
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            notificationManager.createNotificationChannel(channel);

            builder = new NotificationCompat.Builder(getApplicationContext(),"10002");
        }
        else
        {
            builder = new NotificationCompat.Builder(getApplicationContext());
        }
      /*  indb = new InnerDataBase(getApplicationContext(), "datastatistic.db");
        indb.insert(InDBAdapter.insertDataStatisticQuery(true,"2018-05-28","2","1","1","1"));
        indb.insert(InDBAdapter.insertDataStatisticQuery(false,"2018-05-29","1","1","3","2"));
        indb.insert(InDBAdapter.insertDataStatisticQuery(true,"2018-05-30","1","3","4","1"));
        indb.insert(InDBAdapter.insertDataStatisticQuery(false,"2018-05-31","2","3","1","1"));
        indb.insert(InDBAdapter.insertDataStatisticQuery(true,"2018-06-01","1","2","2","2"));
        indb.insert(InDBAdapter.insertDataStatisticQuery(true,"2018-06-02","2","1","2","1"));
        indb.insert(InDBAdapter.insertDataStatisticQuery(true,"2018-06-03","1","3","3","2"));
        indb.close();*/
        //리스타트 서비스 생성
        restartService = new RestartService();
        intent = new Intent(getApplicationContext(), AlarmService.class);

        IntentFilter intentFilter = new IntentFilter("com.hci.healthchatterbox.service.AlarmService");
        //브로드 캐스트에 등록
        registerReceiver(restartService,intentFilter);
        // 서비스 시작
        startService(intent);

    }

    /*public boolean isServiceRunningCheck() {
        ActivityManager manager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.hci.healthchatterbox.service.AlarmService".equals(service.service.getClassName())) {
                Log.i(TAG,"Service Running");
                return true;
            }
        }
        Log.i(TAG,"Service not Running");
        return false;
    }*/
}
