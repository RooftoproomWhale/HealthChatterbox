package com.hci.healthchatterbox.customview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.hci.healthchatterbox.R;
import com.hci.healthchatterbox.activity.SurveyActivity;
import com.hci.healthchatterbox.database.InDBAdapter;
import com.hci.healthchatterbox.database.InnerDataBase;


/**
 * Created by w on 2018-05-01.
 */

public class HealthCheckView extends FrameLayout {
    public CheckBox ch;
    InnerDataBase indb;

    public HealthCheckView(Context context)
    {
        super(context);
        initView();
    }

    public HealthCheckView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        initView();
        getAttrs(attrs);
    }

    public HealthCheckView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context,attrs);
        initView();
        getAttrs(attrs,defStyle);
    }

    private void initView()
    {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater)getContext().getSystemService(infService);
        View v = li.inflate(R.layout.healthgoal_bar,this,false);
        addView(v);

        ch = (CheckBox)findViewById(R.id.healCheckBox);

        /*ch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch.isChecked())
                {
                    new AlertDialog.Builder(getContext())
                            .setTitle("알림")
                            .setMessage("운동을 했을 시에만 체크 가능하며 체크시 변경 불가능합니다. 체크 하시겠습니까?")
                            .setCancelable(false)
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    indb.insert(InDBAdapter.updateExerciseQuery(true));
                                    Intent intent = new Intent(getContext(), SurveyActivity.class);
                                    getContext().startActivity(intent);
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ch.setChecked(false);
                                }
                            })
                            .show();
                }
                else
                {
                    ch.setChecked(true);
                }
            }
        });*/

    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HealCheckImage);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HealCheckImage, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {
        boolean check = typedArray.getBoolean(R.styleable.HealCheckImage_Checked,false);
        ch.setChecked(check);

        typedArray.recycle();
    }

    public void setCheck(Boolean ch) {
        this.ch.setChecked(ch);
    }

}
