package com.example.subik.myshoppinglist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText editUsername;
    TextView textSignup, textAdmin;
    Button btnLogin;
    private static final int REQUEST_SIGNUP = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //btnLogin.setEnabled(false);
                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();
               new android.os.Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        progressDialog.dismiss();
                    }
                }, 3000);


            }
        });

        textSignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Start SignUp Activity
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                //startActivity(i);
                startActivityForResult(i, REQUEST_SIGNUP);
            }
        });

        textAdmin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Start SignUp Activity
                Intent i = new Intent(LoginActivity.this, AdminActivity.class);
                startActivity(i);
                finish();
            }
        });


    }

    //once signup is completed, the result comes here
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                new android.os.Handler().postDelayed(new Runnable() {
                    public void run() {
                        //after signup, presently, after 5s delay subik() is called.
                        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }, 0);
            }
        }
    }


    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    private void init() {
        editUsername = findViewById(R.id.input_userName);
        textSignup = findViewById(R.id.link_signup);
        textAdmin = findViewById(R.id.link_admin);
        btnLogin = findViewById(R.id.btn_login);
    }
}
