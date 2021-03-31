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
public class SurveyFragment extends Fragment {

    private RadioGroup radioGroup;

    public SurveyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_survey, container, false);

        radioGroup = (RadioGroup)rootView.findViewById(R.id.RadioGroup);
        final RadioButton mental = (RadioButton)rootView.findViewById(R.id.mentalRadio);
        final RadioButton mental2 = (RadioButton)rootView.findViewById(R.id.mentalRadio2);
        final RadioButton mental3 = (RadioButton)rootView.findViewById(R.id.mentalRadio3);
        final RadioButton mental4 = (RadioButton)rootView.findViewById(R.id.mentalRadio4);

        mental.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    mental2.setChecked(false);
                    mental3.setChecked(false);
                    mental4.setChecked(false);
                    System.out.println("check : "+mental.getId());
                    System.out.println("check : "+mental2.getId());
                    System.out.println("check : "+mental3.getId());
                    System.out.println("check : "+mental4.getId());
                }
            }
        });

        mental2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    mental.setChecked(false);
                    mental3.setChecked(false);
                    mental4.setChecked(false);
                }
            }
        });

        mental3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    mental2.setChecked(false);
                    mental.setChecked(false);
                    mental4.setChecked(false);
                }
            }
        });

        mental4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    mental2.setChecked(false);
                    mental3.setChecked(false);
                    mental.setChecked(false);
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
