package com.hci.healthchatterbox.database;

import com.hci.healthchatterbox.handmadeclass.UserInfo;

/**
 * Created by w on 2018-04-16.
 */

public class InDBAdapter
{
    public InDBAdapter() {}

    public static String insertUserinfoQuery(UserInfo info)
    {
        String query = "";
        query = "insert into profiletable values('" + info.getName() + "', '" + info.getGender() + "', " + info.getAge() + ", " + info.getStature() + ", " + info.getWeight() + ", '"
                + info.getJob() + "', '" + info.getAddress() + "', " + (info.getDisease() ? 1 : 0)  + ");";

        System.out.println("insertUserinfoQuery : "+query);

        return query;
    }

    public static String insertMessageinfoQuery(String gender, String ages, String bmi)
    {
        String query = "";
        query = "insert into messagetable values('" + gender + "', '" + ages + "', '" + bmi + "',null,null,null,null,null,null,0);";

        System.out.println("insertMessageinfoQuery : "+query);

        return query;
    }

    public static String insertDatainfoQuery()
    {
        String query = "";
        query = "insert into datastoragetable values(" + 0 + ",null,null,null);";

        System.out.println("insertMessageinfoQuery : "+query);

        return query;
    }

    public static String insertDataStatisticQuery(boolean exer, String day, String health, String sleep, String mentality, String nutrition, String cause)
    {
        String query = "";
        query = "insert into datastatisticstable values(" + (exer ? 1 : 0) + ", '" + day + "', '" + health + "', '" + sleep + "', '" + mentality + "', '" + nutrition + "', '" + cause + "');";

        System.out.println("insertDataStatisticQuery : "+query);

        return query;
    }

    public static String updateExerciseQuery(boolean flag)
    {
        String query = "";
        query = "update datastoragetable set isexercise = " + (flag ? 1 : 0) + ";";

        System.out.println("updateExerciseQuery : "+query);

        return query;
    }

    public static String updateMessageTextQuery(String message)
    {
        String query = "";
        query = "update datastoragetable set messagetext = '" + message +"';";

        System.out.println("updateMessageTextQuery : "+query);

        return query;
    }

    public static String updateMessageIndexQuery(int index)
    {
        String query = "";
        query = "update datastoragetable set messageindex = " + index +";";

        System.out.println("updateMessageIndexQuery : "+query);

        return query;
    }

    public static String updateMessageMaxIndexQuery(int index)
    {
        String query = "";
        query = "update datastoragetable set messageindexmax = " + index +";";

        System.out.println("updateMessageMaxIndexQuery : "+query);

        return query;
    }

    public static String updateMentalityQuery(int mental)
    {
        String query = "";
        query = "update messagetable set mentality = " + mental + ";";

        System.out.println("updateMentalityQuery : "+query);

        return query;
    }

    public static String updateHealthQuery(int health)
    {
        String query = "";
        query = "update messagetable set health = " + health + ";";

        System.out.println("updateHealthQuery : "+query);

        return query;
    }

    public static String updateSleepQuery(int sleep)
    {
        String query = "";
        query = "update messagetable set sleep = " + sleep + ";";

        System.out.println("updateHealthQuery : "+query);

        return query;
    }

    public static String updateNutritionQuery(int nutrition)
    {
        String query = "";
        query = "update messagetable set nutrition = " + nutrition + ";";

        System.out.println("updateHealthQuery : "+query);

        return query;
    }

    public static String updateCauseQuery(int Cause)
    {
        String query = "";
        query = "update messagetable set externalcause = " + Cause + ";";

        System.out.println("updateCauseQuery : "+query);

        return query;
    }
}
