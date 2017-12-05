package com.example.subik.myshoppinglist;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subik.myshoppinglist.database.DatabaseManager;
import com.example.subik.myshoppinglist.parsing.Coupon;
import com.example.subik.myshoppinglist.parsing.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LargestDiscountActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editBudget;
    Button buttonGetList;
    LinearLayout linearLayout;
    ListView listProductList;
    ArrayList<String> arrayList = new ArrayList<>();
    //ArrayAdapter<String> arrayAdapter;
    DatabaseManager databaseManager;
    //private final static int offColor = Color.argb(255,220,220,220);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_largest_discount);
        databaseManager = DatabaseManager.getDatabaseManager(this);
        init();

    }

    private void init() {
        editBudget = findViewById(R.id.edit_Budget);
        buttonGetList = findViewById(R.id.button_discount);
        linearLayout = findViewById(R.id.linItemList);
        listProductList = findViewById(R.id.listProductList);
        buttonGetList.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(editBudget.getText().toString().isEmpty()) {
            editBudget.setError("Please enter a budget");
        }else {
            Double budget = Double.valueOf(editBudget.getText().toString());
            ArrayList<Coupon> databaseResult = databaseManager.getAllCoupons();

            Double remainingBudget = budget;
            //Toast.makeText(this,String.valueOf(),Toast.LENGTH_LONG).show();
            //Coupon.DiscountResult result = Coupon.generateLargestDiscount(databaseResult,budget);
            Double cost = 0.0;
            List<String> product_name = new ArrayList<String>();
            List<Coupon> eligibleCoupons = new ArrayList<Coupon>();
            for (int i = 0; i<databaseResult.size(); i++){
                Coupon coupon = databaseResult.get(i);
                if (remainingBudget >= TotalCost(coupon.getId()) && Collections.disjoint(product_name,getProductNames(coupon.getId()))){
                    Log.e("Eligible Products: ", coupon.toString());
                    eligibleCoupons.add(coupon);
                    product_name.addAll(getProductNames(coupon.getId()));
                    remainingBudget = remainingBudget - TotalCost(coupon.getId());
                    cost +=   TotalCost(coupon.getId());
                }
            }


            Double discount = 0.0;
            for (int count =0; count<eligibleCoupons.size(); count++){
                //Log.e("ID: ", String.valueOf(eligibleCoupons.get(count).getId()));
                //Log.e("Discount: ", String.valueOf(eligibleCoupons.get(count).getDiscount()));
                discount += eligibleCoupons.get(count).getDiscount();
                ArrayList<Product> product = eligibleCoupons.get(count).getProductArrayList();
                for (int j = 0; j<product.size(); j++){
                    arrayList.add(product.get(j).getProduct());
                    //Log.e("Names : ", String.valueOf(product.get(j).getProduct()));
                }
            }
            cost = cost -discount;
            linearLayout.removeAllViews();
            TextView txtGenCost = new TextView(getApplicationContext());
            txtGenCost.setText("Total Cost: "+String.format("%.2f",cost)+" After "+String.format("%.2f",discount)+ " Discount");
            txtGenCost.setTextColor(Color.BLUE);
            txtGenCost.setTypeface(Typeface.DEFAULT_BOLD);
            linearLayout.addView(txtGenCost);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
            arrayAdapter.notifyDataSetChanged();
            listProductList.setAdapter(arrayAdapter);
        }

    }

    private List<String> getProductNames(Integer id) {

        ArrayList<Product> databaseResult = databaseManager.selectCouponProductById(id);
        List<String> names = new ArrayList<>();
        for (int i = 0; i<databaseResult.size(); i++){
            String name = databaseResult.get(i).getProduct();
            names.add(name);
        }
        return names;
    }

    private Double TotalCost(Integer id) {
        ArrayList<Product> databaseResult = databaseManager.selectCouponProductById(id);
        Double sum = 0.0;
        for (int i = 0; i<databaseResult.size(); i++){
            Double cost = Double.valueOf(databaseResult.get(i).getPrice());
            sum += cost;
        }
        return sum;
    }
}

























/*

                if (i%2 == 0){
                    //textProduct.setVisibility(View.VISIBLE);
                    listProducts.setBackgroundColor(offColor);
                }

                ArrayList<Product> products = coupon.getProducts();

                for (int j = 0; j < products.size(); j++) {
                    arrayList.add(products.get(j).getProduct());
                }

                Toast.makeText(getApplicationContext(), String.valueOf(arrayList),Toast.LENGTH_LONG).show();

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
                arrayAdapter.notifyDataSetChanged();
                listProducts.setAdapter(arrayAdapter);



<android.support.v4.widget.NestedScrollView
        android:id="@+id/nested_discount_list"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="15dp"
        android:fillViewport="true">



    </android.support.v4.widget.NestedScrollView>
 */


/*


linearLayout.removeAllViews();
            //for (int i =0; i<listProducts.)
            //listProducts.removeAllViews();
            Double budget = Double.parseDouble(editBudget.getText().toString());

            ArrayList<Coupon> databaseResult = databaseManager.getAllCoupons();

            Coupon.DiscountResult result = Coupon.generateLargestDiscount(databaseResult,budget);


            TextView txtGenCost = new TextView(getApplicationContext());
            txtGenCost.setText("Total Cost: "+String.format("%.2f",result.cost)+" After "+String.format("%.2f",result.discount)+ " Discount");
            txtGenCost.setTextColor(Color.BLUE);
            txtGenCost.setTypeface(Typeface.DEFAULT_BOLD);
            linearLayout.addView(txtGenCost);


            for (int i = 0; i < result.coupons.size(); i++) {

                Coupon coupon = result.coupons.get(i);

                LinearLayout layout = new LinearLayout(getApplicationContext());
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layout.setGravity(Gravity.CENTER_VERTICAL);

                LinearLayout linItems = new LinearLayout(getApplicationContext());
                linItems.setOrientation(LinearLayout.VERTICAL);

                TextView txtCouponDiscount = new TextView(getApplicationContext());
                txtCouponDiscount.setText(String.format("%.2f",coupon.getDiscount()));
                txtCouponDiscount.setTextColor(Color.BLACK);

                if(i % 2 == 0){
                    layout.setBackgroundColor(offColor);
                }

                ArrayList<Product> items = coupon.getProducts();
                for (int j = 0; j < items.size(); j++) {
                    TextView txtItem = new TextView(getApplicationContext());
                    txtItem.setTextColor(Color.BLACK);
                    txtItem.setText(items.get(j).getProduct());
                    linItems.addView(txtItem);
                }

                layout.addView(linItems,0);
                layout.addView(txtCouponDiscount,1);
                linearLayout.addView(layout);

                LinearLayout.LayoutParams linItemsParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                linItemsParams.weight = 1;

                LinearLayout.LayoutParams couponDiscountParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                couponDiscountParams.weight = 0;

                linItems.setLayoutParams(linItemsParams);
                txtCouponDiscount.setLayoutParams(couponDiscountParams);


            }

 */