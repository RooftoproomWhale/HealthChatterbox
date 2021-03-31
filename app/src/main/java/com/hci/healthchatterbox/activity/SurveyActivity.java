package com.hci.healthchatterbox.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hci.healthchatterbox.Fragment.SurveyFragment;
import com.hci.healthchatterbox.Fragment.SurveyFragment2;
import com.hci.healthchatterbox.Fragment.SurveyFragment3;
import com.hci.healthchatterbox.Fragment.SurveyFragment4;
import com.hci.healthchatterbox.Fragment.SurveyFragment5;
import com.hci.healthchatterbox.R;
import com.hci.healthchatterbox.database.InDBAdapter;
import com.hci.healthchatterbox.database.InnerDataBase;

public class SurveyActivity extends AppCompatActivity {

    private final int FRAGMENT1 = 1;
    private Button button;
    private InnerDataBase indb;
    private FragmentTransaction transaction;
    private RadioGroup rg;
    private SurveyFragment fragment1;
    private SurveyFragment2 fragment2;
    private SurveyFragment3 fragment3;
    private SurveyFragment4 fragment4;
    private SurveyFragment5 fragment5;
    private Intent intent;
    private int i = 0;
    private int mental,health,sleep,nutri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        indb = new InnerDataBase(getApplicationContext(),"message.db");

        button = (Button)findViewById(R.id.surveybutton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFragment(FRAGMENT1 + i);
            }
        });

        transaction = getFragmentManager().beginTransaction();
        fragment1 = new SurveyFragment(); fragment2 = new SurveyFragment2(); fragment3 = new SurveyFragment3(); fragment4 = new SurveyFragment4(); fragment5 = new SurveyFragment5();
        transaction.replace(R.id.fragment_container, fragment2);
        transaction.commit();
    }
    private void callFragment(int frament_no) {

        // 프래그먼트 사용을 위해
        transaction = getFragmentManager().beginTransaction();

        System.out.println("i : "+frament_no);

        switch (frament_no) {
            case 1:
                rg = fragment2.getRadioCheck();
                System.out.println("check : "+rg.getCheckedRadioButtonId());
                // '프래그먼트1' 호출
                if(rg.getCheckedRadioButtonId() != -1)
                {
                    if(rg.getCheckedRadioButtonId() == R.id.healthRadio)
                    {
                        indb.insert(InDBAdapter.updateHealthQuery(2));
                    }
                    else if(rg.getCheckedRadioButtonId() == R.id.healthRadio2)
                    {
                        indb.insert(InDBAdapter.updateHealthQuery(1));
                    }
                    else if(rg.getCheckedRadioButtonId() == R.id.healthRadio3)
                    {
                        indb.insert(InDBAdapter.updateHealthQuery(1));
                    }
                    transaction.replace(R.id.fragment_container, fragment3);
                    transaction.commit();
                    i++;
                }
                else
                {
                    Toast.makeText(this,"항목중에 하나를 선택해주세요.",Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                rg = fragment3.getRadioCheck();
                System.out.println("check : "+rg.getCheckedRadioButtonId());
                // '프래그먼트1' 호출
                if(rg.getCheckedRadioButtonId() != -1)
                {
                    if(rg.getCheckedRadioButtonId() == R.id.sleepRadio)
                    {
                        indb.insert(InDBAdapter.updateSleepQuery(1));
                    }
                    else if(rg.getCheckedRadioButtonId() == R.id.sleepRadio2)
                    {
                        indb.insert(InDBAdapter.updateSleepQuery(2));
                    }
                    else if(rg.getCheckedRadioButtonId() == R.id.sleepRadio3)
                    {
                        indb.insert(InDBAdapter.updateSleepQuery(3));
                    }
                    transaction.replace(R.id.fragment_container, fragment4);
                    transaction.commit();
                    i++;
                }
                else
                {
                    Toast.makeText(this,"항목중에 하나를 선택해주세요.",Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                rg = fragment4.getRadioCheck();
                System.out.println("check : "+rg.getCheckedRadioButtonId());
                // '프래그먼트1' 호출
                if(rg.getCheckedRadioButtonId() != -1)
                {
                    if(rg.getCheckedRadioButtonId() == R.id.nutriRadio)
                    {
                        indb.insert(InDBAdapter.updateNutritionQuery(1));
                    }
                    else if(rg.getCheckedRadioButtonId() == R.id.nutriRadio2)
                    {
                        indb.insert(InDBAdapter.updateNutritionQuery(1));
                    }
                    else if(rg.getCheckedRadioButtonId() == R.id.nutriRadio3)
                    {
                        indb.insert(InDBAdapter.updateNutritionQuery(2));
                    }
                    indb.close();
                    indb = new InnerDataBase(getApplicationContext(),"datastorage.db");
                    if(indb.getResult("select isexercise from datastoragetable").equals("0"))
                    {
                        indb.close();
                        transaction.replace(R.id.fragment_container, fragment1);
                        transaction.commit();
                        i++;
                        indb = new InnerDataBase(getApplicationContext(),"message.db");
                    }
                    else
                    {
                        indb.close();
                        intent = new Intent();
                        intent.putExtra("isSuccessed",1);
                        setResult(RESULT_OK,intent);
                        finish();
                    }

                }
                else
                {
                    Toast.makeText(this,"항목중에 하나를 선택해주세요.",Toast.LENGTH_SHORT).show();
                }
                break;
            case 4:
                rg = fragment1.getRadioCheck();
                System.out.println("check : "+rg.getCheckedRadioButtonId());
                // '프래그먼트1' 호출
                if(rg.getCheckedRadioButtonId() != -1)
                {
                    if(rg.getCheckedRadioButtonId() == R.id.mentalRadio)
                    {
                        indb.insert(InDBAdapter.updateMentalityQuery(3));
                    }
                    else if(rg.getCheckedRadioButtonId() == R.id.mentalRadio2)
                    {
                        indb.insert(InDBAdapter.updateMentalityQuery(4));
                    }
                    else if(rg.getCheckedRadioButtonId() == R.id.mentalRadio3)
                    {
                        indb.insert(InDBAdapter.updateMentalityQuery(2));
                    }
                    else if(rg.getCheckedRadioButtonId() == R.id.mentalRadio4)
                    {
                        indb.insert(InDBAdapter.updateMentalityQuery(1));
                    }
                    transaction.replace(R.id.fragment_container, fragment5);
                    transaction.commit();
                    i++;
                }
                else
                {
                    Toast.makeText(this,"항목중에 하나를 선택해주세요.",Toast.LENGTH_SHORT).show();
                }
                break;
            case 5:
                rg = fragment5.getRadioCheck();
                System.out.println("check : "+rg.getCheckedRadioButtonId());
                // '프래그먼트1' 호출
                if(rg.getCheckedRadioButtonId() != -1)
                {
                    if(rg.getCheckedRadioButtonId() == R.id.causeRadio)
                    {
                        indb.insert(InDBAdapter.updateCauseQuery(1));
                    }
                    else if(rg.getCheckedRadioButtonId() == R.id.causeRadio2)
                    {
                        indb.insert(InDBAdapter.updateCauseQuery(2));
                    }
                    else if(rg.getCheckedRadioButtonId() == R.id.causeRadio3)
                    {
                        indb.insert(InDBAdapter.updateCauseQuery(3));
                    }
                    else if(rg.getCheckedRadioButtonId() == R.id.causeRadio3)
                    {
                        indb.insert(InDBAdapter.updateCauseQuery(4));
                    }
                    indb.close();
                    intent = new Intent();
                    intent.putExtra("isSuccessed",1);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                else
                {
                    Toast.makeText(this,"항목중에 하나를 선택해주세요.",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
