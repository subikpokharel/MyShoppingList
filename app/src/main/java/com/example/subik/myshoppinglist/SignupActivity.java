package com.example.subik.myshoppinglist;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity {

    EditText editName, editUser;
    Button btnSignup;
    TextView textLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();

        //on Create Account clicked
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

        //on Login Clicked
        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    private void signup() {
        btnSignup.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onSignupSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    private void onSignupSuccess() {

        //Returns to the LoginActivity

        btnSignup.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    private void init() {
        editName = findViewById(R.id.signup_name);
        editUser = findViewById(R.id.signup_username);
        btnSignup = findViewById(R.id.btn_signup);
        textLogin = findViewById(R.id.link_login);
    }
}
