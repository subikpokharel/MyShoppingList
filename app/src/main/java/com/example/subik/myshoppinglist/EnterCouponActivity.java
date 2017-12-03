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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subik.myshoppinglist.adapter.CouponEnterAdapter;
import com.example.subik.myshoppinglist.database.DatabaseManager;
import com.example.subik.myshoppinglist.myapplication.MyApplication;
import com.example.subik.myshoppinglist.parsing.Product;

import java.util.ArrayList;

public class EnterCouponActivity extends AppCompatActivity {

    EditText editDiscount;
    ListView listProducts;
    DatabaseManager databaseManager;
    ArrayList<Product> listArrayProducts;
    MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_coupon);
        databaseManager = DatabaseManager.getDatabaseManager(this);
        init();
        listArrayProducts = databaseManager.getEnteredProducts();
        bindData();
    }

    private void bindData() {
        CouponEnterAdapter productAdapter = new CouponEnterAdapter(this,R.layout.coupon_products,listArrayProducts);
        listProducts.setAdapter(productAdapter);
    }

    private void init() {
        editDiscount = findViewById(R.id.textDiscount);
        //btnEnterCoupon = findViewById(R.id.buttonCoupon);
        listProducts = findViewById(R.id.listProducts);
    }


    public  void enterCoupon(View view){
        CheckBox checkBox;
        ListView listView = findViewById(R.id.listProducts);
        ArrayList<Integer> ids = new ArrayList<>();
        //Toast.makeText(getApplicationContext(),String.valueOf(listView.getChildCount()),Toast.LENGTH_LONG).show();
        for (int i = 0; i < listView.getChildCount(); i++) {
            checkBox = listView.getChildAt(i).findViewById(R.id.chkCouponProduct);
            //Log.e("Number: ", String.valueOf(checkBox));
            if (checkBox.isChecked()){
                TextView textView = listView.getChildAt(i).findViewById(R.id.productName);
                int id = databaseManager.getProductId(textView.getText().toString());
                //Log.e("Updated: ", textView.getText().toString());
                ids.add(id);
                //Log.e("Updated: ", String.valueOf(id));
            }
        }
        //Log.e("id length", String.valueOf(ids.size()));
        if (ids.size()>0 && !(editDiscount.getText().toString()).isEmpty()){
            long result = databaseManager.insertCoupons(Float.parseFloat(editDiscount.getText().toString()));
            if (result != -1){
                databaseManager.insertCouponProducts(result,ids);
            }
            Intent intent = new Intent(this, EnterCouponActivity.class);
            Toast.makeText(getApplicationContext(),"Coupon Successfully Added", Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();

        }else{
            Toast.makeText(getApplicationContext(),"Please Select the Products or Enter Discount", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Dashboard.class);
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
        if (id == R.id.nav_dashboard){
            Intent i = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(i);
            finish();
        }
        if (id == R.id.nav_enterProduct){
            Intent i = new Intent(getApplicationContext(), EnterProductActivity.class);
            startActivity(i);
            finish();
        }
        if (id == R.id.nav_enterCoupon){
            Intent i = new Intent(getApplicationContext(), EnterCouponActivity.class);
            startActivity(i);
            finish();
        }


        if (id == R.id.nav_listCoupon){
            Intent i = new Intent(getApplicationContext(), ListCouponActivity.class);
            startActivity(i);
            finish();
        }
        if (id == R.id.nav_logout) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
            // Setting Dialog Title
            alertDialog.setTitle("Confirm Logout...");

            // Setting Dialog Message
            alertDialog.setMessage("Are you sure you want to Logout?");

            alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Logging Out...");
                    progressDialog.show();
                    new android.os.Handler().postDelayed(new Runnable() {
                        public void run() {

                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            myApplication.removeToken("Admin_Name");
                            startActivity(i);
                            finish();
                            progressDialog.dismiss();
                        }
                    }, 1000);

                }
            });

            // Setting Negative "NO" Btn
            alertDialog.setNegativeButton("Discard",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            // Showing Alert Dialog
            alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
