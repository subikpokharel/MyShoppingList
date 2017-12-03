package com.example.subik.myshoppinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subik.myshoppinglist.adapter.ListCouponByIDAdapter;
import com.example.subik.myshoppinglist.database.DatabaseManager;
import com.example.subik.myshoppinglist.parsing.Product;

import java.util.ArrayList;

public class ViewCouponActivity extends AppCompatActivity {

    TextView textId, textDiscount;
    ListView listCoupon;
    ArrayList<Product> databaseResult;
    DatabaseManager databaseManager;
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

    }
}
