package com.example.subik.myshoppinglist;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subik.myshoppinglist.adapter.ListCouponsAdapter;
import com.example.subik.myshoppinglist.database.DatabaseManager;
import com.example.subik.myshoppinglist.myapplication.MyApplication;

import java.util.ArrayList;

public class ListCouponActivity extends AppCompatActivity implements ListCouponsAdapter.DataTransferInterface{

    DatabaseManager databaseManager;
    ArrayList<Integer> listIds;
    MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_coupon);
        databaseManager = DatabaseManager.getDatabaseManager(this);
        listIds = databaseManager.getCouponIds();
        bindData();

    }

    private void bindData() {
        ListView listView = findViewById(R.id.listCouponList);
        ListCouponsAdapter listCouponsAdapter = new ListCouponsAdapter(this,R.layout.coupon_list,listIds);
        listView.setAdapter(listCouponsAdapter);
    }




   @Override
    public void sendID(final int id) {
       databaseManager.deleteCoupon(id);
       Intent i = new Intent(getApplicationContext(), ListCouponActivity.class);
       startActivity(i);
       finish();
       //Toast.makeText(getApplicationContext(),"Hello: "+id,Toast.LENGTH_LONG).show();
    }

    @Override
    public void viewCoupon(int id) {
        Intent intent = new Intent(getApplicationContext(), ViewCouponActivity.class);
        intent.putExtra("Coupon Id",String.valueOf(id));
        startActivity(intent);
        finish();
    }

    @Override
    public void makeHyperlink(TextView textView) {
        SpannableStringBuilder ssb = new SpannableStringBuilder( );
        ssb.append( textView.getText( ) );
        ssb.setSpan( new URLSpan("#"), 0, ssb.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
        textView.setText( ssb, TextView.BufferType.SPANNABLE );
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
