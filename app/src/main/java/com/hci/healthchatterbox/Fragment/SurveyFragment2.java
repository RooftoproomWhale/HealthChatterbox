package com.hci.healthchatterbox.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hci.healthchatterbox.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyFragment2 extends Fragment {

    private RadioGroup radioGroup;

    public SurveyFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_survey2, container, false);

        radioGroup = (RadioGroup)rootView.findViewById(R.id.RadioGroup);
        final RadioButton health = (RadioButton)rootView.findViewById(R.id.healthRadio);
        final RadioButton health2 = (RadioButton)rootView.findViewById(R.id.healthRadio2);
        final RadioButton health3 = (RadioButton)rootView.findViewById(R.id.healthRadio3);

        health.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    health2.setChecked(false);
                    health3.setChecked(false);
                }
            }
        });

        health2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    health.setChecked(false);
                    health3.setChecked(false);
                }
            }
        });

        health3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    health2.setChecked(false);
                    health.setChecked(false);
                }
            }
        });

        return rootView;
    }

    public RadioGroup getRadioCheck()
    {
        return radioGroup;
    }

}
