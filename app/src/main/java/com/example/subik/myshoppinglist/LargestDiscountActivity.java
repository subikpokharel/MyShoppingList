package com.example.subik.myshoppinglist;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.Collection;
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

        if(editBudget.getText().toString().isEmpty() || (Double.parseDouble(editBudget.getText().toString()) == 0)) {
            editBudget.setError("Please enter a budget");
        }else {
            Double budget = Double.valueOf(editBudget.getText().toString());
            ArrayList<Coupon> databaseResult = databaseManager.getAllCoupons();

            //Log.e("Size: ", String.valueOf(budget));
            List<Coupon> finalCoupons =  new ArrayList<>();
            double maximumDiscount = 0;

            //Log.e("Size: ", String.valueOf(databaseResult.size()));
            for (int i = 0; i<databaseResult.size(); i++){
                Coupon coupon = databaseResult.get(i);
                //System.out.println("Main coupon: "+coupon.getDiscount().toString());
                List<String> productNames = new ArrayList<String>();
                List<Coupon> eligibleCoupons = new ArrayList<>();
                Double remainingBudget = budget;
                double maximumCurrentDiscount = 0;
                remainingBudget = remainingBudget-TotalCost(coupon.getId())+coupon.getDiscount();
                if (remainingBudget<0)
                    continue;
                eligibleCoupons.add(coupon);
                productNames.addAll(getProductNames(coupon.getId()));
                maximumCurrentDiscount+=coupon.getDiscount();
                //System.out.println("product names: "+productNames.toString());
                //System.out.println("Remaining: "+remainingBudget.toString());

                for (int j = 0; j<databaseResult.size(); j++){
                    Coupon coupon1 = databaseResult.get(j);
                    if (coupon1.getId().equals(coupon.getId()))
                        continue;

                    //System.out.println("ID going to check: "+coupon1.getId().toString());

                    if (remainingBudget >= (TotalCost(coupon1.getId())-coupon1.getDiscount()) && Collections.disjoint(productNames,getProductNames(coupon1.getId()))){
                        //System.out.println("Eligible Products: "+coupon1.toString());
                        eligibleCoupons.add(coupon1);
                        productNames.addAll(getProductNames(coupon1.getId()));
                        //remainingBudget = remainingBudget - TotalCost(coupon.getId());
                        remainingBudget = remainingBudget-TotalCost(coupon1.getId())+coupon1.getDiscount();
                        maximumCurrentDiscount += coupon1.getDiscount();
                        //cost +=   TotalCost(coupon.getId());
                    }
                }
                //System.out.println("Current: "+maximumCurrentDiscount);
                //System.out.println("max : "+maximumDiscount);
                if (maximumCurrentDiscount>maximumDiscount){
                    /*System.out.println("Eligible coupons with discount: "+ maximumCurrentDiscount);
                    for (Coupon coupon3:eligibleCoupons){
                        System.out.println(coupon3.toString());
                    }*/
                    maximumDiscount = maximumCurrentDiscount;
                    finalCoupons = new ArrayList<>();
                    finalCoupons.addAll(eligibleCoupons);
                }

            }


            arrayList.clear();
            Double discount = 0.0;
            Double cost = 0.0;
            for (int count =0; count<finalCoupons.size(); count++){
                //Log.e("ID: ", String.valueOf(eligibleCoupons.get(count).getId()));
                //Log.e("Discount: ", String.valueOf(eligibleCoupons.get(count).getDiscount()));
                discount += finalCoupons.get(count).getDiscount();
                ArrayList<Product> product = finalCoupons.get(count).getProductArrayList();
                for (int j = 0; j<product.size(); j++){
                    arrayList.add(product.get(j).getProduct());
                    cost += Double.parseDouble(product.get(j).getPrice());
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

            //ArrayAdapter<String> arrayAdapter = new ArrayAdapter();

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
        //System.out.print("Names Received: "+ names.toString());
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



/*Double cost = 0.0;
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
            }*/




/*

 List<Coupon> eligibleCoupons = new ArrayList<>();
            List<Coupon> finalCoupons = new ArrayList<>();
            double maximumDiscount = 0;

            for (int i = 0; i < databaseResult.size(); i++) {
                Coupon coupon = databaseResult.get(i);
                System.out.println("Main coupon: " + coupon.toString());
                //selected items array
                List<String> productNames = new ArrayList<String>();
                //double remainingBudget = budget;
                eligibleCoupons = new ArrayList<>();
                double maximumCurrentDiscount = 0;
                eligibleCoupons.add(coupon);
                productNames.addAll(getProductNames(coupon.getId()));
                remainingBudget = remainingBudget - TotalCost(coupon.getId()) + coupon.getDiscount();
                maximumCurrentDiscount += coupon.getDiscount();

                for (int j = 0; j < databaseResult.size(); j++) {
                    Coupon coupon1 = databaseResult.get(j);
                    if (coupon1.getId() == coupon.getId())
                        continue;
                    Log.e(">>>>>Inside", coupon1.toString());
                    Log.e("REmaining budget: ", String.valueOf(remainingBudget));
                    if (remainingBudget >= (TotalCost(coupon1.getId()) - coupon1.getDiscount()) && Collections.disjoint(productNames, getProductNames(coupon1.getId()))) {
                        Log.e("Eligible Products: ", coupon1.toString());
                        eligibleCoupons.add(coupon1);
                        productNames.addAll(getProductNames(coupon1.getId()));
                        remainingBudget = remainingBudget - TotalCost(coupon1.getId()) + coupon1.getDiscount();
                        //cost +=   TotalCost(coupon1.getId());
                        maximumCurrentDiscount += coupon1.getDiscount();
                    }
                }

                if (maximumCurrentDiscount > maximumDiscount) {
                    Log.e("E coupon w discount: ", String.valueOf(maximumCurrentDiscount));
                    for (Coupon coupon3 : eligibleCoupons) {
                        Log.e("Finally: ", coupon3.toString());
                    }
                    maximumDiscount = maximumCurrentDiscount;
                    finalCoupons = new ArrayList<>();
                    finalCoupons.addAll(eligibleCoupons);
                }

            }



            arrayList.clear();
            Double discount = 0.0;
            Double cost = 0.0;
            for (int count =0; count<finalCoupons.size(); count++){
                //Log.e("ID: ", String.valueOf(eligibleCoupons.get(count).getId()));
                //Log.e("Discount: ", String.valueOf(eligibleCoupons.get(count).getDiscount()));
                discount += finalCoupons.get(count).getDiscount();
                ArrayList<Product> product = finalCoupons.get(count).getProductArrayList();
                for (int j = 0; j<product.size(); j++){
                    arrayList.add(product.get(j).getProduct());
                    cost += Double.parseDouble(product.get(j).getPrice());
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

            //ArrayAdapter<String> arrayAdapter = new ArrayAdapter();


            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
            arrayAdapter.notifyDataSetChanged();
            listProductList.setAdapter(arrayAdapter);

 */


















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