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
public class SurveyFragment4 extends Fragment {

    private RadioGroup radioGroup;

    public SurveyFragment4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_survey4, container, false);

        radioGroup = (RadioGroup)rootView.findViewById(R.id.RadioGroup);
        final RadioButton nutri = (RadioButton)rootView.findViewById(R.id.nutriRadio);
        final RadioButton nutri2 = (RadioButton)rootView.findViewById(R.id.nutriRadio2);
        final RadioButton nutri3 = (RadioButton)rootView.findViewById(R.id.nutriRadio3);

        nutri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    nutri2.setChecked(false);
                    nutri3.setChecked(false);
                }
            }
        });

        nutri2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    nutri.setChecked(false);
                    nutri3.setChecked(false);
                }
            }
        });

        nutri3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    nutri2.setChecked(false);
                    nutri.setChecked(false);
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