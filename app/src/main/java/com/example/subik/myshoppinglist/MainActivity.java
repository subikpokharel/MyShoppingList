package com.example.subik.myshoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView progressBar = (TextView) findViewById(R.id.progressBarText);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setText(R.string.app_open_msg);
                progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale));
            }
        },500);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },4000);


    }
}
