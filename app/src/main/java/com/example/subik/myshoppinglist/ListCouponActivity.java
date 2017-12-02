package com.example.subik.myshoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
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
    public void sendID(int id) {
       //databaseManager.deleteCoupon(id);
       Intent intent = new Intent(getApplicationContext(), ListCouponActivity.class);
       startActivity(intent);
       finish();
       Toast.makeText(getApplicationContext(),"Hello: "+id,Toast.LENGTH_LONG).show();
    }
}
