package com.hci.healthchatterbox.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.hci.healthchatterbox.R;
import com.hci.healthchatterbox.database.InnerDataBase;

import java.util.ArrayList;

public class IntroActivity extends AppCompatActivity {
    private Intent intent;
    private static final String TAG = "IntroActivity";
    private boolean isPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                isPermission = true;
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("퍼미션을 거부하신다면 앱의 기능을 사용할 수 없습니다. [설정] -> [권한] 에서 설정 변경을 부탁드립니다")
                .setPermissions(Manifest.permission.INTERNET,
                        Manifest.permission.VIBRATE,
                        Manifest.permission.WAKE_LOCK,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.RECEIVE_BOOT_COMPLETED)
                .check();

        findViewById(R.id.startbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"" + isCheckAppFirstExe());
                if(isPermission&&isNetwork()&&isGps())
                {
                    if(isCheckAppFirstExe())
                    {
                        intent = new Intent(getApplicationContext(),PersonalActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("checkuserflag", 1);
                        startActivity(intent);
                        finish();
                    }
                }
                else if(isPermission==false)
                {
                    Toast.makeText(getApplicationContext(),"앱 기능에 대한 권한이 거부돼 앱을 사용할 수 없습니다",Toast.LENGTH_SHORT).show();
                }
                else if(isNetwork()==false)
                {
                    Toast.makeText(getApplicationContext(),"데이터나 와이파이가 꺼져있어 앱을 사용할 수 없습니다",Toast.LENGTH_SHORT).show();
                }
                else if(isGps()==false)
                {
                    Toast.makeText(getApplicationContext(),"GPS가 꺼져있어 앱을 사용할 수 없습니다",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isCheckAppFirstExe()
    {
        InnerDataBase indb = new InnerDataBase(getApplicationContext(), "profile.db");
        String check = indb.getResult("select * from profiletable;");

        Log.i(TAG,"" + check.isEmpty());

        return check.isEmpty();
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
    private boolean isGps()
    {
        boolean gpsEnable = false;
        LocationManager manager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            gpsEnable = true;
        }
        return gpsEnable;
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.i(TAG,"onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
    }
}
