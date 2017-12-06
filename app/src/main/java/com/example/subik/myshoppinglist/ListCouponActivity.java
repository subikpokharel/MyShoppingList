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
       //Toast.makeText(getApplicationContext(),"Hello: "+id,Toast.LENGTH_LONG).show();
    }

    @Override
    public void viewCoupon(int id) {
        Intent intent = new Intent(getApplicationContext(), ViewCouponActivity.class);
        intent.putExtra("Coupon Id",String.valueOf(id));
        startActivity(intent);
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
