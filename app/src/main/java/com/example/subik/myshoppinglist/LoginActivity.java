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
                startActivity(i);
                //startActivityForResult(i, REQUEST_SIGNUP);
            }
        });

        textAdmin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Start SignUp Activity
                Intent i = new Intent(LoginActivity.this, AdminActivity.class);
                //startActivityForResult(i, REQUEST_SIGNUP);
                startActivity(i);
                finish();
            }
        });


    }

    private void init() {
        editUsername = findViewById(R.id.input_userName);
        textSignup = findViewById(R.id.link_signup);
        textAdmin = findViewById(R.id.link_admin);
        btnLogin = findViewById(R.id.btn_login);
    }
}
