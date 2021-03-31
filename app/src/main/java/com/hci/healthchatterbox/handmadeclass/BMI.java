package com.hci.healthchatterbox.handmadeclass;

import java.io.Serializable;

/**
 * Created by w on 2018-04-27.
 */

public class BMI implements Serializable{
    int height, weight, age, returnbmi;
    double bmi;
    public BMI(int in_height, int in_weight, int in_age)
    {
        height = in_height;
        weight = in_weight;
        age = in_age;
    }

    public int calculateBMI()
    {
        bmi = (double)(weight / (height * height));
        if(age > 18)
        {
            if(bmi < 18.5)
            {
                returnbmi = 1;
            }
            else if(bmi < 23)
            {
                returnbmi = 2;
            }
            else
            {
                returnbmi = 3;
            }
        }
        else
        {
            if(bmi < 18.2)
            {
                returnbmi = 1;
            }
            else if(bmi < 25.18)
            {
                returnbmi = 2;
            }
            else
            {
                returnbmi = 3;
            }
        }

        return returnbmi;
    }
}
