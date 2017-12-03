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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subik.myshoppinglist.adapter.ListCouponByIDAdapter;
import com.example.subik.myshoppinglist.database.DatabaseManager;
import com.example.subik.myshoppinglist.myapplication.MyApplication;
import com.example.subik.myshoppinglist.parsing.Product;

import java.util.ArrayList;

public class ViewCouponActivity extends AppCompatActivity {

    TextView textId, textDiscount;
    ListView listCoupon;
    ArrayList<Product> databaseResult;
    DatabaseManager databaseManager;
    MyApplication myApplication;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_coupon);
        databaseManager = DatabaseManager.getDatabaseManager(this);
        init();
        id = getIntent().getStringExtra("Coupon Id");
        getData(id);
        textId.setText(id);

        bindData();
        //Toast.makeText(getApplicationContext(),"ID Clicked: "+id,Toast.LENGTH_LONG).show();
    }

    private void bindData() {
        ListCouponByIDAdapter listCouponByIDAdapter = new ListCouponByIDAdapter(this,R.layout.view_coupon_by_id,databaseResult);
        listCoupon.setAdapter(listCouponByIDAdapter);
    }

    private void getData(String id) {
        int coupon_id = Integer.parseInt(id);
        float dis = databaseManager.selectDiscount(coupon_id);
        textDiscount.setText(String.valueOf(dis));
        //Toast.makeText(getApplicationContext(),"ID Clicked: "+String.valueOf(dis),Toast.LENGTH_LONG).show();
        databaseResult = databaseManager.selectCouponProductById(coupon_id);
    }

    private void init() {
        textId = findViewById(R.id.textViewCouponId);
        textDiscount = findViewById(R.id.textViewDiscount);
        listCoupon = findViewById(R.id.listViewCoupon);
    }

    public void deleteCoupon(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewCouponActivity.this);
        // Setting Dialog Title
        alertDialog.setTitle("Delete Coupon...");

        // Setting Dialog Message
        alertDialog.setMessage("Do you want to delete Coupon :"+id+" ?");

        alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                final ProgressDialog progressDialog = new ProgressDialog(ViewCouponActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Deleting Coupon...");
                progressDialog.show();
                new android.os.Handler().postDelayed(new Runnable() {
                    public void run() {

                        databaseManager.deleteCoupon(Integer.parseInt(id));
                        Intent i = new Intent(getApplicationContext(), ListCouponActivity.class);
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
