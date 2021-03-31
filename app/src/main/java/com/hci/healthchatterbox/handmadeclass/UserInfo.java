package com.hci.healthchatterbox.handmadeclass;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by w on 2018-04-16.
 */

public class UserInfo implements Serializable
{
    private String name;
    private String gender;
    private int age;
    private int stature;
    private int weight;
    private String job;
    private String address;
    private Boolean disease;
    private BMI bmi;

    private static final String TAG = "Userinfo";

    public UserInfo(String in_name, String in_gender, int in_age, int in_stature, int in_weight, String in_job, String in_address, Boolean in_disease)
    {
        name = in_name;
        gender = in_gender;
        age = in_age;
        stature = in_stature;
        weight = in_weight;
        job = in_job;
        address = in_address;
        disease = in_disease;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getStrGender()
    {
        if(gender.equals("남"))
        {
            return "1";
        }
        else
        {
            return "2";
        }
    }


    public int getAge() {
        return age;
    }

    public String getStrAge()
    {
        int intage = 0;
        if(age > 60)
        {
            intage = 5;
        }
        else if(age < 20)
        {
            intage = 1;
        }
        else
        {
            intage = (age/10)-1;
        }
        String ages = Integer.toString(intage);

        return ages;
    }

    public int getStature() {
        return stature;
    }

    public int getWeight() {
        return weight;
    }

    public String getJob() {
        return job;
    }

    public String getAddress() {
        return address;
    }

    public Boolean getDisease() {
        return disease;
    }

    public String getBMI()
    {
        bmi = new BMI(stature,weight,age);
        return Integer.toString(bmi.calculateBMI());
    }

    public void show()
    {
        Log.i(TAG, name + ", " + gender + ", " + age + ", " + stature + ", " + weight + ", " + job + ", " + address + ", " + disease);
    }
}
