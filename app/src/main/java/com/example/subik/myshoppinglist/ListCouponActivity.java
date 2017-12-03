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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subik.myshoppinglist.adapter.ListCouponsAdapter;
import com.example.subik.myshoppinglist.database.DatabaseManager;

import java.util.ArrayList;

public class ListCouponActivity extends AppCompatActivity implements ListCouponsAdapter.DataTransferInterface{

    DatabaseManager databaseManager;
    ArrayList<Integer> listIds;

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
    }

    @Override
    public void makeHyperlink(TextView textView) {
        SpannableStringBuilder ssb = new SpannableStringBuilder( );
        ssb.append( textView.getText( ) );
        ssb.setSpan( new URLSpan("#"), 0, ssb.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
        textView.setText( ssb, TextView.BufferType.SPANNABLE );
    }
}
