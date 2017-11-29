package com.example.subik.myshoppinglist.myapplication;

import android.app.Application;
import android.graphics.Typeface;

/**
 * Created by subik on 11/29/17.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initFont();
    }

    public static class Fonts{

        public static Typeface FontIcon;
    }

    private void initFont() {
        Fonts.FontIcon = Typeface.create(Typeface.createFromAsset(getAssets(),"fonts.ttf"),Typeface.NORMAL);
    }
}
