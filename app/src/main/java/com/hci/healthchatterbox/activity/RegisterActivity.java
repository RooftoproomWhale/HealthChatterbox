package com.hci.healthchatterbox.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.hci.healthchatterbox.R;
import com.hci.healthchatterbox.handmadeclass.UserInfo;
import com.hci.healthchatterbox.database.InDBAdapter;
import com.hci.healthchatterbox.database.InnerDataBase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class RegisterActivity extends AppCompatActivity {
    private Intent intent;
    private static final String TAG = "RegisterActivity";
    private String strAddress,strGender = "남";
    private MaterialEditText meName, meAge, meCm, meWeight, meJob;
    private CheckBox cMale, cFemale, cTrue, cFalse;
    private Spinner sAddress;
    private ArrayAdapter<CharSequence> adspin;
    private UserInfo userInfo;
    private InnerDataBase indb = null;
    private boolean isKorean(String str){
        return str.matches("^[가-힣]*$");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        meName = (MaterialEditText)findViewById(R.id.nameedittext);
        meAge = (MaterialEditText)findViewById(R.id.ageedittext);
        meCm = (MaterialEditText)findViewById(R.id.cmedittext);
        meWeight = (MaterialEditText)findViewById(R.id.kgedittext);
        meJob = (MaterialEditText)findViewById(R.id.jobEditText);
        cMale = (CheckBox)findViewById(R.id.malecheck); cFemale = (CheckBox)findViewById(R.id.femalecheck);
        cTrue = (CheckBox)findViewById(R.id.truecheck); cFalse = (CheckBox)findViewById(R.id.falsecheck);

        cMale.setOnClickListener(new CheckBox.OnClickListener()
        {
            @Override
            public void onClick(View v)
            { // TODO : process the click event.
                strGender = "남";
                if(cFemale.isChecked())
                {
                    cFemale.setChecked(false);
                }
            }
        });

        cFemale.setOnClickListener(new CheckBox.OnClickListener()
        {
            @Override
            public void onClick(View v)
            { // TODO : process the click event.
                strGender = "여";
                if(cMale.isChecked())
                {
                    cMale.setChecked(false);
                }
            }
        });

        cTrue.setOnClickListener(new CheckBox.OnClickListener()
        {
            @Override
            public void onClick(View v)
            { // TODO : process the click event.
                if(cFalse.isChecked())
                {
                    cFalse.setChecked(false);
                }
            }
        });

        cFalse.setOnClickListener(new CheckBox.OnClickListener()
        {
            @Override
            public void onClick(View v)
            { // TODO : process the click event.
                if(cTrue.isChecked())
                {
                    cTrue.setChecked(false);
                }
            }
        });

        sAddress = (Spinner)findViewById(R.id.addressspinner);

        adspin = ArrayAdapter.createFromResource(getApplicationContext(),R.array.city,android.R.layout.simple_spinner_item);
        adspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sAddress.setAdapter(adspin);

        sAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strAddress = adspin.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        findViewById(R.id.homebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,""+ (meAge.isCharactersCountValid() && meAge.validate("\\d+","나이를 입력하세요")));
                if((meName.validate("^[가-힣]*$","이름을 입력하세요") && meName.isCharactersCountValid())&&
                        (meAge.isCharactersCountValid() && meAge.validate("\\d+","나이를 입력하세요"))&&
                        (meCm.isCharactersCountValid() && meCm.validate("\\d+","키를 입력하세요"))&&
                        (meWeight.isCharactersCountValid() && meWeight.validate("\\d+","몸무게를 입력하세요"))&&
                        (meJob.isCharactersCountValid()) && meJob.validate("^[가-힣]*$","직업을 입력하세요"))
                {
                    intent = new Intent(getBaseContext(),MainActivity.class);
                    userInfo = new UserInfo(meName.getText().toString(),strGender,Integer.parseInt(meAge.getText().toString()),Integer.parseInt(meCm.getText().toString()),
                            Integer.parseInt(meWeight.getText().toString()),meJob.getText().toString(),strAddress,cTrue.isChecked());

                    userInfo.show();

                    indb = new InnerDataBase(getApplicationContext(), "profile.db");
                    indb.insert(InDBAdapter.insertUserinfoQuery(userInfo));
                    indb.close();

                    indb = new InnerDataBase(getApplicationContext(), "message.db");
                    indb.insert(InDBAdapter.insertMessageinfoQuery(userInfo.getStrGender(),userInfo.getStrAge(),userInfo.getBMI()));
                    indb.close();

                    indb = new InnerDataBase(getApplicationContext(), "datastorage.db");
                    indb.insert(InDBAdapter.insertDatainfoQuery());
                    indb.close();

                    Log.i(TAG, "mOnClick - user db check");
                    indb = new InnerDataBase(getApplicationContext(), "profile.db");
                    Log.i(TAG, indb.getResult("select * from profiletable"));
                    Log.i(TAG, "mOnClick - user db check done");

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userinfo", userInfo);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
