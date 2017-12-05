package com.example.subik.myshoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subik.myshoppinglist.adapter.CouponEnterAdapter;
import com.example.subik.myshoppinglist.database.DatabaseManager;
import com.example.subik.myshoppinglist.parsing.Coupon;
import com.example.subik.myshoppinglist.parsing.Product;

import java.util.ArrayList;

public class BestDiscountActivity extends AppCompatActivity {

    TextView textDiscount;
    ListView listView;
    DatabaseManager databaseManager;
    ArrayList<Product> listArrayProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_discount);
        databaseManager = DatabaseManager.getDatabaseManager(this);
        init();
        listArrayProducts = databaseManager.getEnteredProducts();
        bindData();
    }

    private void bindData() {
        CouponEnterAdapter productAdapter = new CouponEnterAdapter(this,R.layout.coupon_products,listArrayProducts);
        listView.setAdapter(productAdapter);
    }

    private void init() {
        textDiscount = findViewById(R.id.textViewDiscount);
        listView = findViewById(R.id.list_item_products);
    }


    public  void btnBestDiscount(View view){
        CheckBox checkBox;
        double originalCost = 0.00;
        ArrayList<Product> products = new ArrayList<>();
        for (int i = 0; i < listView.getChildCount(); i++) {
            checkBox = listView.getChildAt(i).findViewById(R.id.chkCouponProduct);
            //Log.e("Number: ", String.valueOf(checkBox));
            if (checkBox.isChecked()){
                TextView textView = listView.getChildAt(i).findViewById(R.id.productName);
                Product databaseResult = databaseManager.getProductByName(textView.getText().toString());
                originalCost += Double.parseDouble(databaseResult.getPrice());
                products.add(databaseResult);
                /*Log.e("Updated: ", String.valueOf(textView.getText()));
                Log.e("Price: ", String.valueOf(originalCost));*/
            }
        }
        Log.e("Price: ", String.valueOf(originalCost));

    }
}















//Log.e("id length", String.valueOf(ids.size()));

        /*ArrayList<Coupon> coupons = databaseManager.getAllCoupons();
        for (int i = coupons.size() - 1; i >= 0; i--) {
            boolean bad = false;

            for (int j = 0; j < coupons.get(i).getProducts().size(); j++) {


                if(!products.contains(coupons.get(i).getProducts().get(j))){ //if we haven't checked a product the coupon contains, remove it
                    bad = true;
                    break;
                }
            }
            if(bad)
                coupons.remove(i);
        }
        Log.e("id length", String.valueOf(coupons.size()));
        Coupon.DiscountResult result = Coupon.generateLargestDiscount(coupons);
        Log.e("Discount: ", String.valueOf(result.discount));
        textDiscount.setText("" + String.format("%.2f",originalCost - result.discount) + " after a discount of " + String.format("%.2f",result.discount));
*/