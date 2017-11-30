package com.example.subik.myshoppinglist;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subik.myshoppinglist.database.DatabaseManager;
import com.example.subik.myshoppinglist.parsing.Customer;

public class SignupActivity extends AppCompatActivity {

    EditText editName, editUser;
    Button btnSignup;
    TextView textLogin;
    DatabaseManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        manager = DatabaseManager.getDatabaseManager(this);
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
        if (!validate()){
            onSignupFailed();
            return;
        }
        btnSignup.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = editName.getText().toString();
        String username = editUser.getText().toString();
        Customer customer = new Customer();
        customer.setName(name);
        customer.setUsername(username);
        long status = manager.signup(customer);
        //Toast.makeText(getBaseContext(), String.valueOf(status), Toast.LENGTH_LONG).show();
        if (status != -1){
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onLoginSuccess or onLoginFailed
                            onSignupSuccess();
                            progressDialog.dismiss();
                        }
                    }, 3000);
        }else{
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On failed call onSignupFailed
                            progressDialog.dismiss();
                            onSignupFailed();
                        }
                    }, 2000);

        }
    }

    private void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();
        editName.setText("");
        editUser.setText("");
        btnSignup.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;
        String name = editName.getText().toString();
        String username = editUser.getText().toString();

        if (name.isEmpty()) {
            editName.setError("Please enter a name");
            valid = false;
        } else {
            editName.setError(null);
        }
        if (username.isEmpty()) {
            editUser.setError("Please enter a valid username");
            valid = false;
        } else {
            editName.setError(null);
        }
        return valid;
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
