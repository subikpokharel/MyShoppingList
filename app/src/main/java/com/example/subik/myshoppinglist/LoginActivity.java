package com.example.subik.myshoppinglist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subik.myshoppinglist.database.DatabaseHandler;
import com.example.subik.myshoppinglist.database.DatabaseManager;
import com.example.subik.myshoppinglist.myapplication.MyApplication;

public class LoginActivity extends AppCompatActivity {

    EditText editUsername, editPassword;
    TextView textSignup, textCustomer, textAdmin;
    Button btnCustomerLogin, btnAdminLogin;
    RelativeLayout customerLayout, adminLayout;
    private static final int REQUEST_SIGNUP = 0;
    DatabaseManager databaseManager;
    MyApplication myApplication;
    private String[] databaseresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //databaseHandler = new DatabaseHandler(this);
        databaseManager = DatabaseManager.getDatabaseManager(this);
        myApplication = (MyApplication) getApplication();
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
              //  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();

        btnCustomerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //btnLogin.setEnabled(false);
               functionLogin();
            }
        });
        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();
                final String password = editPassword.getText().toString();
                new android.os.Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (password.equals("admin")){
                            Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                            myApplication.saveToken("Admin_Name", "admin");
                            startActivity(intent);
                            editPassword.setText("");
                            progressDialog.dismiss();
                        }else{
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                            editPassword.setText("");
                            progressDialog.dismiss();
                        }

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

    private void functionLogin() {
        if (!validate()) {
            //if the entered details are not valid, goto the following function
            onLoginFailed();
            return;
        }
        btnCustomerLogin.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        String username = editUsername.getText().toString();
        databaseresult = databaseManager.login(username);
        if (!databaseresult[0].equals("Not Found")){
            new android.os.Handler().postDelayed(new Runnable() {
                public void run() {
                    /*Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);*/
                    onLoginSuccess();
                    progressDialog.dismiss();
                }
            }, 3000);
        }else{
            new android.os.Handler().postDelayed(new Runnable() {
                public void run() {
                    progressDialog.dismiss();
                    onLoginFailed();
                }
            }, 2000);
        }

    }

    private void onLoginSuccess() {
        btnCustomerLogin.setEnabled(true);
        String username = editUsername.toString();
        String name = databaseresult[0];
        String id = databaseresult[1];
        editUsername.setText("");
        //Toast.makeText(LoginActivity.this,name+" "+ id, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        myApplication.saveToken("Customer_Name", name);
        myApplication.saveToken("Customer_Username", username);
        myApplication.saveToken("Customer_ID", id);
        startActivity(intent);
    }

    private void onLoginFailed() {
        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
        btnCustomerLogin.setEnabled(true);
        editUsername.setText("");
    }

    private boolean validate() {
        boolean valid = true;
        String username = editUsername.getText().toString();
        if (username.isEmpty()) {
            editUsername.setError("Please enter a valid username");
            valid = false;
        } else {
            editUsername.setError(null);
        }
        return valid;
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
        LoginActivity.makeTextViewHyperlink(textSignup);

    }

    private static void makeTextViewHyperlink(TextView textSignup) {
        SpannableStringBuilder ssb = new SpannableStringBuilder( );
        ssb.append( textSignup.getText( ) );
        ssb.setSpan( new URLSpan("#"), 0, ssb.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
        textSignup.setText( ssb, TextView.BufferType.SPANNABLE );


    }

    View.OnClickListener listner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.customertxt:
                    textCustomer.setTextColor(getResources().getColor(R.color.colorAccent));
                    textAdmin.setTextColor(getResources().getColor(R.color.dividerColor));
                    textCustomer.setTypeface(Typeface.DEFAULT_BOLD);
                    textAdmin.setTypeface(Typeface.DEFAULT);
                    customerLayout.setVisibility(View.VISIBLE);
                    adminLayout.setVisibility(View.GONE);
                    break;
                case R.id.admintxt:
                    textAdmin.setTextColor(getResources().getColor(R.color.colorAccent));
                    textCustomer.setTextColor(getResources().getColor(R.color.dividerColor));
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
