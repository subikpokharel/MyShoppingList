package com.example.subik.myshoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.subik.myshoppinglist.adapter.CouponEnterAdapter;
import com.example.subik.myshoppinglist.database.DatabaseManager;
import com.example.subik.myshoppinglist.parsing.Product;

import java.util.ArrayList;

public class CouponActivity extends AppCompatActivity {

    EditText editDiscount;
    Button btnEnterCoupon;
    ListView listProducts;
    DatabaseManager databaseManager;
    ArrayList<Product> listArrayProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
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
        btnEnterCoupon = findViewById(R.id.buttonCoupon);
        listProducts = findViewById(R.id.listProducts);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        finish();
    }
}
