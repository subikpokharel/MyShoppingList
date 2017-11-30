package com.example.subik.myshoppinglist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText editUsername, editPassword;
    TextView textSignup, textCustomer, textAdmin;
    Button btnCustomerLogin, btnAdminLogin;
    RelativeLayout customerLayout, adminLayout;
    private static final int REQUEST_SIGNUP = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
              //  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();

        btnCustomerLogin.setOnClickListener(new View.OnClickListener() {
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
        btnCustomerLogin = findViewById(R.id.btn_login);
        editPassword = findViewById(R.id.admin_password);
        btnAdminLogin = findViewById(R.id.btn_admin_login);
        customerLayout = findViewById(R.id.customerLogin);
        adminLayout = findViewById(R.id.adminLogin);
        textCustomer = findViewById(R.id.customertxt);
        textAdmin = findViewById(R.id.admintxt);
        textCustomer.setOnClickListener(listner);
        textAdmin.setOnClickListener(listner);
    }

    View.OnClickListener listner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.customertxt:
                    textCustomer.setTextColor(getResources().getColor(R.color.black));
                    textAdmin.setTextColor(getResources().getColor(R.color.colorIcons));
                    textCustomer.setTypeface(Typeface.DEFAULT_BOLD);
                    textAdmin.setTypeface(Typeface.DEFAULT);
                    customerLayout.setVisibility(View.VISIBLE);
                    adminLayout.setVisibility(View.GONE);
                    break;
                case R.id.admintxt:
                    textAdmin.setTextColor(getResources().getColor(R.color.black));
                    textCustomer.setTextColor(getResources().getColor(R.color.colorIcons));
                    textAdmin.setTypeface(Typeface.DEFAULT_BOLD);
                    textCustomer.setTypeface(Typeface.DEFAULT);
                    adminLayout.setVisibility(View.VISIBLE);
                    customerLayout.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    };
}
