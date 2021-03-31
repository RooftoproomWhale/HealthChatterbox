package com.hci.healthchatterbox.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.hadiidbouk.charts.BarData;
import com.hadiidbouk.charts.ChartProgressBar;
import com.hci.healthchatterbox.R;
import com.hci.healthchatterbox.database.InnerDataBase;

import java.util.ArrayList;


public class DataActivity extends AppCompatActivity {

    private static final String TAG = "DataActivity";
    private TabHost tabHost;
    private ImageButton imageButton;
    private DonutProgress donutProgress;
    private TextView tab1Text, tab2Text, tab3Text;
    private InnerDataBase indb;
    private String health, sleep, mental, nutri, cause;
    private ArrayList<ArrayList<String>> arrayList;
    private ChartProgressBar chartProgressBar,mentalchartProgressBar ,sleepchartProgressBar ,nutrichartProgressBar, causechartProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        imageButton = (ImageButton)findViewById(R.id.backimageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tab1Viewinit();
        tab2Viewinit();
        tab3Viewinit();

        tabHost = (TabHost)findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("1").setContent(R.id.tab1).setIndicator("일간 분석");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("2").setContent(R.id.tab2).setIndicator("주간 분석");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("3").setContent(R.id.tab3).setIndicator("월간 분석");

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
    }

    public void tab1Viewinit()
    {
        indb = new InnerDataBase(getApplicationContext(),"datastorage.db");
        tab1Text = (TextView)findViewById(R.id.ExercizetextView);
        TextView tx = (TextView)findViewById(R.id.textView13);
        TextView mentaltx = (TextView)findViewById(R.id.tablementaltextView);
        TableLayout tableLayout = (TableLayout)findViewById(R.id.tablelayout);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.examLinear);
        ImageView sleepimage = (ImageView)findViewById(R.id.sleepImage);
        ImageView healthimage = (ImageView)findViewById(R.id.healthImage);
        ImageView mentalimage = (ImageView)findViewById(R.id.mentalImage);
        ImageView nutriimage = (ImageView)findViewById(R.id.nutriImage);
        ImageView exerimage = (ImageView)findViewById(R.id.ExercizeImage);


        if(indb.getResult("select isexercise from datastoragetable").equals("0"))
        {
            tab1Text.setText("당신은 오늘 하루 아직 운동을 하지 않았습니다.");
            exerimage.setImageResource(R.drawable.red_circle);
        }
        else if(indb.getResult("select isexercise from datastoragetable").equals("1"))
        {
            tab1Text.setText("당신은 오늘 하루 운동 목표량을 달성했습니다.");
            exerimage.setImageResource(R.drawable.blue_circle);
        }
        indb.close();

        indb = new InnerDataBase(getApplicationContext(),"message.db");
        health = indb.getResult("select health from messagetable"); Log.i(TAG,health);
        sleep = indb.getResult("select sleep from messagetable"); Log.i(TAG,sleep);
        mental = indb.getResult("select mentality from messagetable"); Log.i(TAG,mental);
        nutri = indb.getResult("select nutrition from messagetable"); Log.i(TAG,nutri);
        cause = indb.getResult("select externalcause from messagetable"); Log.i(TAG,cause);

        if(!mental.equals("0")&&!mental.equals("null"))
        {
            if(mental.equals("3"))
            {
                mentalimage.setImageResource(R.drawable.yellow_circle);
            }
            else if(mental.equals("4"))
            {
                mentalimage.setImageResource(R.drawable.yellow_circle);
            }
            else if(mental.equals("2"))
            {
                mentalimage.setImageResource(R.drawable.red_circle);
            }
            else if(mental.equals("1"))
            {
                mentalimage.setImageResource(R.drawable.blue_circle);
            }
        }
        else
        {
            mentalimage.setVisibility(View.INVISIBLE);
            mentaltx.setVisibility(View.INVISIBLE);
        }


        Log.i(TAG, String.valueOf((!health.equals("0")&&!health.equals("null"))));
        if((!health.equals("0")&&!health.equals("null"))&&
                (!sleep.equals("0")&&!sleep.equals("null"))&&
                /*(!mental.equals("0")&&!mental.equals("null"))&&*/
                (!nutri.equals("0")&&!nutri.equals("null")))
        {
            if(health.equals("1"))
            {
                healthimage.setImageResource(R.drawable.blue_circle);
            }
            else if(health.equals("2"))
            {
                healthimage.setImageResource(R.drawable.red_circle);
            }

            if(sleep.equals("1"))
            {
                sleepimage.setImageResource(R.drawable.red_circle);
            }
            else if(sleep.equals("3"))
            {
                sleepimage.setImageResource(R.drawable.yellow_circle);
            }
            else if(sleep.equals("2"))
            {
                sleepimage.setImageResource(R.drawable.blue_circle);
            }

            if(mental.equals("3"))
            {
                mentalimage.setImageResource(R.drawable.yellow_circle);
            }
            else if(mental.equals("4"))
            {
                mentalimage.setImageResource(R.drawable.yellow_circle);
            }
            else if(mental.equals("2"))
            {
                mentalimage.setImageResource(R.drawable.red_circle);
            }
            else if(mental.equals("1"))
            {
                mentalimage.setImageResource(R.drawable.blue_circle);
            }

            if(nutri.equals("2"))
            {
                nutriimage.setImageResource(R.drawable.red_circle);
            }
            else if(nutri.equals("1"))
            {
                nutriimage.setImageResource(R.drawable.blue_circle);
            }
        }
        else
        {
            tableLayout.setVisibility(View.INVISIBLE);
            exerimage.setVisibility(View.INVISIBLE);
            linearLayout.setVisibility(View.INVISIBLE);
            tx.setText("설문조사 데이터가 없습니다.");
        }
        indb.close();
    }

    public void tab2Viewinit()
    {
        int exer = 0; int health= 0 ; int mental = 0; int sleep = 0; int nutri = 0; int cause = 0;
        String strhealth = ""; String strmental = ""; String strsleep = ""; String strnutri = ""; String strcause = "";
        indb = new InnerDataBase(getApplicationContext(),"datastatistic.db");
        arrayList = indb.getArrayResult("select * from datastatisticstable");
        Log.i(TAG,arrayList+"");
        Log.i(TAG,arrayList.size()+"");
        indb.close();

        TextView tx = (TextView)findViewById(R.id.tab2textView);
        TextView healthtx = (TextView)findViewById(R.id.textViewhealth);
        TextView mentaltx = (TextView)findViewById(R.id.textViewmental);
        TextView sleeptx = (TextView)findViewById(R.id.textViewsleep);
        TextView nutritx = (TextView)findViewById(R.id.textViewnutri);
        TextView causetx = (TextView)findViewById(R.id.textViewcause);
        donutProgress = (DonutProgress)findViewById(R.id.donutProgress);
        chartProgressBar = (ChartProgressBar)findViewById(R.id.HealthChartProgressBar);
        mentalchartProgressBar = (ChartProgressBar)findViewById(R.id.MentalChartProgressBar);
        sleepchartProgressBar = (ChartProgressBar)findViewById(R.id.SleepChartProgressBar);
        nutrichartProgressBar = (ChartProgressBar)findViewById(R.id.NutriChartProgressBar);
        //causechartProgressBar = (ChartProgressBar)findViewById(R.id.causeChartProgressBar);

        ArrayList<BarData> healthdataList = new ArrayList<>();
        BarData healthdata;
        ArrayList<BarData> mentaldataList = new ArrayList<>();
        BarData mentaldata;
        ArrayList<BarData> sleepdataList = new ArrayList<>();
        BarData sleepdata;
        ArrayList<BarData> nutridataList = new ArrayList<>();
        BarData nutridata;
        //ArrayList<BarData> causedataList = new ArrayList<>();
        //BarData causedata;

        if(arrayList.size()>6)
        {
            /*for(int i = arrayList.size()-1; i>=arrayList.size()-7; i--)*/
            for(int i = arrayList.size()-5; i<arrayList.size(); i++)
            {
                if(arrayList.get(i).get(0).equals("1"))
                {
                    exer++;
                }

                if(arrayList.get(i).get(2).equals("1"))
                {
                    health = 2;
                    strhealth = "좋음";
                }
                else if(arrayList.get(i).get(2).equals("2"))
                {
                    health = 1;
                    strhealth = "나쁨";
                }

                healthdata = new BarData(arrayList.get(i).get(1),health,strhealth);
                healthdataList.add(healthdata);

                if(arrayList.get(i).get(3).equals("1"))
                {
                    sleep = 1;
                    strsleep = "부족";
                }
                else if(arrayList.get(i).get(3).equals("2"))
                {
                    sleep = 3;
                    strsleep = "정상";
                }
                else if(arrayList.get(i).get(3).equals("3"))
                {
                    sleep = 2;
                    strsleep = "과다";
                }

                sleepdata = new BarData(arrayList.get(i).get(1),sleep,strsleep);
                sleepdataList.add(sleepdata);

                if(arrayList.get(i).get(4).equals("4"))
                {
                    mental = 2;
                    strmental = "바쁨,피곤";
                }
                else if(arrayList.get(i).get(4).equals("3"))
                {
                    mental = 2;
                    strmental = "귀찮음";
                }
                else if(arrayList.get(i).get(4).equals("2"))
                {
                    mental = 1;
                    strmental = "우울 및 무기력";
                }
                else if(arrayList.get(i).get(4).equals("1"))
                {
                    mental = 3;
                    strmental = "좋음";
                }
                else if(arrayList.get(i).get(4).equals("0"))
                {
                    mental = 0;
                    strmental = "운동함";
                }

                mentaldata = new BarData(arrayList.get(i).get(1),mental,strmental);
                mentaldataList.add(mentaldata);

                if(arrayList.get(i).get(5).equals("1"))
                {
                    nutri = 2;
                    strnutri = "보통 및 소식";
                }
                else if(arrayList.get(i).get(5).equals("2"))
                {
                    nutri = 1;
                    strnutri = "과식";
                }

                nutridata = new BarData(arrayList.get(i).get(1),nutri,strnutri);
                nutridataList.add(nutridata);
/*
                if(arrayList.get(i).get(6).equals("4"))
                {
                    cause = 1;
                    strcause = "약속";
                }
                else if(arrayList.get(i).get(6).equals("3"))
                {
                    cause = 1;
                    strcause = "대기";
                }
                else if(arrayList.get(i).get(6).equals("2"))
                {
                    cause = 1;
                    strcause = "날씨";
                }
                else if(arrayList.get(i).get(6).equals("1"))
                {
                    cause = 1;
                    strcause = "일";
                }
                else if(arrayList.get(i).get(6).equals("0"))
                {
                    cause = 0;
                    strcause = "운동함";
                }

                causedata = new BarData(arrayList.get(i).get(1),cause,strcause);
                causedataList.add(causedata);*/
            }
            chartProgressBar.setDataList(healthdataList);
            chartProgressBar.setMaxValue(2);
            chartProgressBar.build();
            sleepchartProgressBar.setDataList(sleepdataList);
            sleepchartProgressBar.setMaxValue(3);
            sleepchartProgressBar.build();
            mentalchartProgressBar.setDataList(mentaldataList);
            mentalchartProgressBar.setMaxValue(3);
            mentalchartProgressBar.build();
            nutrichartProgressBar.setDataList(nutridataList);
            nutrichartProgressBar.setMaxValue(2);
            nutrichartProgressBar.build();
           /* causechartProgressBar.setDataList(causedataList);
            causechartProgressBar.setMaxValue(1);
            causechartProgressBar.build();*/

            donutProgress.setUnfinishedStrokeColor(Color.WHITE);
            donutProgress.setFinishedStrokeColor(Color.BLUE);
            donutProgress.setTextColor(Color.BLUE);
            float progress = (float)((float)exer/7.0)*100;
            donutProgress.setProgress(Float.parseFloat(String.format("%.2f",progress)));
            Log.i(TAG,exer+" "+arrayList.size());
            tx.setText("주간 운동 목표 성취률");

            causetx.setVisibility(View.INVISIBLE);
        }
        else
        {
            donutProgress.setVisibility(View.INVISIBLE);
            chartProgressBar.setVisibility(View.INVISIBLE);
            sleepchartProgressBar.setVisibility(View.INVISIBLE);
            mentalchartProgressBar.setVisibility(View.INVISIBLE);
            nutrichartProgressBar.setVisibility(View.INVISIBLE);
//            causechartProgressBar.setVisibility(View.INVISIBLE);
            healthtx.setVisibility(View.INVISIBLE);
            mentaltx.setVisibility(View.INVISIBLE);
            sleeptx.setVisibility(View.INVISIBLE);
            nutritx.setVisibility(View.INVISIBLE);
            causetx.setVisibility(View.INVISIBLE);

            tx.setText("주간 데이터가 모이질 않았습니다.");
        }
    }

    public void tab3Viewinit()
    {
        TextView tx = (TextView)findViewById(R.id.tab3textView);
        tx.setText("월간 데이터가 모이질 않았습니다");
    }
}
