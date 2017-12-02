package com.example.subik.myshoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subik.myshoppinglist.adapter.CouponEnterAdapter;
import com.example.subik.myshoppinglist.database.DatabaseManager;
import com.example.subik.myshoppinglist.parsing.Product;

import java.util.ArrayList;

public class CouponActivity extends AppCompatActivity {

    EditText editDiscount;
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
            Intent intent = new Intent(this, CouponActivity.class);
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
}
