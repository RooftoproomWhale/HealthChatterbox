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
public class SurveyFragment5 extends Fragment {

    private RadioGroup radioGroup;

    public SurveyFragment5() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_survey5, container, false);

        radioGroup = (RadioGroup)rootView.findViewById(R.id.RadioGroup);
        final RadioButton cause = (RadioButton)rootView.findViewById(R.id.causeRadio);
        final RadioButton cause2 = (RadioButton)rootView.findViewById(R.id.causeRadio2);
        final RadioButton cause3 = (RadioButton)rootView.findViewById(R.id.causeRadio3);
        final RadioButton cause4 = (RadioButton)rootView.findViewById(R.id.causeRadio3);

        cause.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    cause2.setChecked(false);
                    cause3.setChecked(false);
                    cause4.setChecked(false);
                }
            }
        });

        cause2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    cause.setChecked(false);
                    cause3.setChecked(false);
                    cause4.setChecked(false);
                }
            }
        });

        cause3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    cause2.setChecked(false);
                    cause.setChecked(false);
                    cause4.setChecked(false);
                }
            }
        });

        cause4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    cause2.setChecked(false);
                    cause.setChecked(false);
                    cause3.setChecked(false);
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
