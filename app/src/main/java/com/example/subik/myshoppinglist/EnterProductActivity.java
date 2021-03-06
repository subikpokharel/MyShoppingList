package com.example.subik.myshoppinglist;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.subik.myshoppinglist.database.DatabaseManager;
import com.example.subik.myshoppinglist.myapplication.MyApplication;
import com.example.subik.myshoppinglist.parsing.Product;

public class EnterProductActivity extends AppCompatActivity {

    EditText editName, editPrice;
    Button buttonEnter;
    MyApplication myApplication;
    DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_product);
        databaseManager = DatabaseManager.getDatabaseManager(this);
        init();


        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterProduct();
            }
        });
    }

    private void enterProduct() {
        if (!validate()){
            onEnterFailed();
            return;
        }
        buttonEnter.setEnabled(false);
        String name = editName.getText().toString();
        String price = editPrice.getText().toString();
        Product product = new Product();
        product.setPrice(price);
        product.setProduct(name);
        long status = databaseManager.enterProduct(product);
        //Toast.makeText(getBaseContext(), String.valueOf(status), Toast.LENGTH_LONG).show();
        if (status != -1){
            Toast.makeText(getBaseContext(), "Product Entered with ID: "+String.valueOf(status), Toast.LENGTH_LONG).show();
            editName.setText("");
            editPrice.setText("");
            buttonEnter.setEnabled(true);
        }else
            onEnterFailed();

    }

    private void onEnterFailed() {
        Toast.makeText(getBaseContext(), "Product could not be entered.", Toast.LENGTH_LONG).show();
        buttonEnter.setEnabled(true);
    }


    private boolean validate() {
        boolean valid = true;
        String name = editName.getText().toString();
        String price = editPrice.getText().toString();

        if (name.isEmpty()) {
            editName.setError("Please enter a name");
            valid = false;
        } else {
            editName.setError(null);
        }
        if (price.isEmpty()) {
            editPrice.setError("Please enter a price");
            valid = false;
        } else {
            editPrice.setError(null);
        }
        return valid;
    }

    private void init() {
        editName = findViewById(R.id.product_name);
        editPrice = findViewById(R.id.product_price);
        buttonEnter = findViewById(R.id.enter_product);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ListProductActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_listProduct){
            Intent i = new Intent(getApplicationContext(), ListProductActivity.class);
            startActivity(i);
        }
        if (id == R.id.nav_enterProduct){
            Intent i = new Intent(getApplicationContext(), EnterProductActivity.class);
            startActivity(i);
        }
        if (id == R.id.nav_enterCoupon){
            Intent i = new Intent(getApplicationContext(), EnterCouponActivity.class);
            startActivity(i);
        }

        if (id == R.id.nav_listCoupon){
            Intent i = new Intent(getApplicationContext(), ListCouponActivity.class);
            startActivity(i);
        }
        if (id == R.id.nav_largest_discount){
            Intent i = new Intent(getApplicationContext(), LargestDiscountActivity.class);
            startActivity(i);
        }
        if (id == R.id.nav_best_discount){
            Intent i = new Intent(getApplicationContext(), BestDiscountActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }


}
