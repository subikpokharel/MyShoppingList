package com.example.subik.myshoppinglist.myapplication;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;

/**
 * Created by subik on 11/29/17.
 */

public class MyApplication extends Application {

    SharedPreferences sharedPreferences;
    @Override
    public void onCreate() {
        super.onCreate();
        initFont();
        sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    public static class Fonts{

        public static Typeface FontIcon;
    }

    private void initFont() {
        Fonts.FontIcon = Typeface.create(Typeface.createFromAsset(getAssets(),"fonts.ttf"),Typeface.NORMAL);
    }

    public void  saveToken(String key, String value){
        SharedPreferences.Editor mEditor = sharedPreferences.edit();
        mEditor.putString(key, value);
        mEditor.apply();

    }

    public String getSavedValue(String key){

        return  sharedPreferences.getString(key,null);
    }

    public void  removeToken(String key){
        SharedPreferences.Editor mEditor = sharedPreferences.edit();
        mEditor.remove(key);
        //mEditor.putString(key, value);
        mEditor.apply();

    }

}
