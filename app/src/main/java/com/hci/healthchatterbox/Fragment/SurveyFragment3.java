package com.hci.healthchatterbox.Fragment;


import android.app.Fragment;
import android.os.Bundle;
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
public class SurveyFragment3 extends Fragment {

    private RadioGroup radioGroup;

    public SurveyFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_survey3, container, false);

        radioGroup = (RadioGroup)rootView.findViewById(R.id.RadioGroup);
        final RadioButton sleep = (RadioButton)rootView.findViewById(R.id.sleepRadio);
        final RadioButton sleep2 = (RadioButton)rootView.findViewById(R.id.sleepRadio2);
        final RadioButton sleep3 = (RadioButton)rootView.findViewById(R.id.sleepRadio3);

        sleep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    sleep2.setChecked(false);
                    sleep3.setChecked(false);
                }
            }
        });

        sleep2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    sleep.setChecked(false);
                    sleep3.setChecked(false);
                }
            }
        });

        sleep3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    sleep2.setChecked(false);
                    sleep.setChecked(false);
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