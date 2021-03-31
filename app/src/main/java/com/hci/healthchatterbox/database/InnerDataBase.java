package com.hci.healthchatterbox.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by w on 2018-04-09.
 */

public class InnerDataBase extends SQLiteOpenHelper
{
    private static final String TAG = "InnerDataBase";
    private static final int DBversion = 1;
    SQLiteDatabase database;

    public InnerDataBase(Context context, String name){
        super(context, name, null, DBversion);

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        database = sqLiteDatabase;
        database.execSQL("CREATE TABLE profiletable (" +
        " 'name' VARCHAR(10) NOT NULL," +
        " 'sex' VARCHAR(2) NULL," +
        " 'age' INT NULL," +
        " 'stature' INT NULL," +
        " 'weight' INT NULL," +
        " 'job' VARCHAR(20) NULL," +
        " 'address' VARCHAR(50) NULL," +
        " 'disease' TINYINT NULL);");

        database.execSQL("CREATE TABLE messagetable (" +
        " 'sex' VARCHAR(2) NULL," +
        " 'ages' VARCHAR(2) NULL," +
        " 'obesity' VARCHAR(2) NULL," +
        " 'health' VARCHAR(2) NULL," +
        " 'mentality' VARCHAR(2) NULL," +
        " 'sleep' VARCHAR(2) NULL," +
        " 'weather' VARCHAR(2) NULL," +
        " 'dust' VARCHAR(2) NULL," +
        " 'nutrition' VARCHAR(2) NULL," +
        " 'externalcause' VARCHAR(2) NULL);");

        database.execSQL("CREATE TABLE datastoragetable (" +
        " 'isexercise' TINYINT NULL," +
        " 'messageindex' INT NULL," +
        " 'messagetext' VARCHAR(200) NULL," +
        " 'messageindexmax' INT NULL);");

        /*database.execSQL("CREATE TABLE datastatisticstable (" +
        " 'dataindex' INT NOT NULL," +
        " 'isexercise' TINYINT NULL," +
        " 'messageindex' INT NULL," +
        " 'messagetext' VARCHAR(200) NULL," +
        " 'startday' VARCHAR(20) NULL," +
        " 'health' VARCHAR(2) NULL," +
        " 'sleep' VARCHAR(2) NULL," +
        " 'mentality' VARCHAR(2) NULL," +
        " 'nutrition' VARCHAR(2) NULL," +
        " PRIMARY KEY ('dataindex'));");*/

        database.execSQL("CREATE TABLE datastatisticstable (" +
        " 'isexercise' TINYINT NULL," +
        " 'strday' VARCHAR(20) NULL," +
        " 'health' VARCHAR(2) NULL," +
        " 'sleep' VARCHAR(2) NULL," +
        " 'mentality' VARCHAR(2) NULL," +
        " 'nutrition' VARCHAR(2) NULL," +
        " 'externalcause' VARCHAR(2) NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean insert(String query)
    {
        SQLiteDatabase db = getWritableDatabase();
        try
        {
            db.execSQL(query);
        }
        catch (Exception e)
        {
            db.close();
            e.printStackTrace();
            return false;
        }
        db.close();
        return true;
    }

    public String getResult(String query){
        SQLiteDatabase db = getWritableDatabase();
        String result = "";

        try {
            db= getReadableDatabase();

            Cursor cs = db.rawQuery(query,null);
            while(cs.moveToNext()) {
                for (int i = 0; i < cs.getColumnCount(); i++) {
                    result += cs.getString(i);
                }
            }
           /* while(cs.moveToNext()){
                for(int i= 0; i<cs.getColumnCount(); i++){
                    result += (cs.getString(i)+"&");
                }
                result += "#";
            }*/
        }catch (Exception e){
            db.close();
            e.printStackTrace();
            return "";
        }
        db.close();

        return result;
    }

    public ArrayList<ArrayList<String>> getArrayResult(String query){
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<String> result = null;
        ArrayList<ArrayList<String>> arrayList = new ArrayList<>();

        try {
            db= getReadableDatabase();

            Cursor cs = db.rawQuery(query,null);
            while(cs.moveToNext()){
                result = new ArrayList<>();
                for(int i= 0; i<cs.getColumnCount(); i++){
                    Log.i("DataActivity",cs.getColumnCount() + " " + cs.getCount() + " " + cs.getString(i));
                    result.add(cs.getString(i));
                }
                arrayList.add(result);

            }
        }catch (Exception e){
            db.close();
            e.printStackTrace();
            return null;
        }
        db.close();

        return arrayList;
    }

    public boolean dropTables(){
        database = getWritableDatabase();
        try
        {
            database.execSQL("drop table "+super.getDatabaseName()+";");
        }
        catch (Exception e){
            database.close();
            e.printStackTrace();
            return false;
        }
        database.close();
        return true;
    }
}
