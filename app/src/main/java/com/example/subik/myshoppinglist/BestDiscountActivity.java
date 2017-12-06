package com.example.subik.myshoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.Collection;
import java.util.List;

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
        ArrayList<String> test = new ArrayList<>();
        for (int i = 0; i < listView.getChildCount(); i++) {
            checkBox = listView.getChildAt(i).findViewById(R.id.chkCouponProduct);
            //Log.e("Number: ", String.valueOf(checkBox));
            if (checkBox.isChecked()){
                TextView textView = listView.getChildAt(i).findViewById(R.id.productName);
                Product databaseResult = databaseManager.getProductByName(textView.getText().toString());
                originalCost += Double.parseDouble(databaseResult.getPrice());
                products.add(databaseResult);
                test.add(databaseResult.getProduct());

                /*Log.e("Updated: ", String.valueOf(textView.getText()));
                Log.e("Price: ", String.valueOf(originalCost));*/
                Log.e("Product Names: ", databaseResult.getProduct());
            }
        }
        Log.e("Price: ", String.valueOf(originalCost));

        ArrayList<Coupon> coupons = databaseManager.getAllCoupons();
        Log.e("Database Size: ", String.valueOf(coupons.size()));

        for (int i = coupons.size() - 1; i >= 0; i--) {
            boolean bad = false;

            for (int j = 0; j < coupons.get(i).getProducts().size(); j++) {

                //Log.e("Products I guess: ",coupons.get(i).getProductArrayList().get(j).getProduct());
                //Log.e("Checking For: ",products);
                if (!test.contains(coupons.get(i).getProductArrayList().get(j).getProduct())){
                    //Log.e("Not There: ", coupons.get(i).getProductArrayList().get(j).getProduct());
                    bad = true;
                    break;
                }
            }
            if(bad)
                coupons.remove(i);

        }

        Log.e("Final Size: ", String.valueOf(coupons.size()));


        /*for (int i = coupons.size() - 1; i >= 0; i--) {
            for (int j = 0; j < coupons.get(i).getProducts().size(); j++) {
                System.out.println("Valid coupon is: "+coupons.get(i).getProductArrayList().get(j).getProduct());
            }

        }*/

        //maximize the discount
        double maximumDiscount = 0;
        List<String> purchaseNames = new ArrayList<>();

        for(Coupon validCoupon: coupons) {
            double currentMaximum = validCoupon.getDiscount();
            for (Product product : products) {
                purchaseNames.add(product.getProduct());
            }
            //            ArrayList<Product> product = validCoupon.getProductArrayList();


            //remove products from purchase list
            purchaseNames.removeAll(getProductNames(validCoupon.getId()));

            for (Coupon validCoupon1: coupons){

                if (purchaseNames.size()==0)
                    break;

                //check with remaining available coupon
                if (!(validCoupon1.getId()).equals(validCoupon.getId())) {

                    boolean isValidCoupon = purchaseNames.containsAll(getProductNames(validCoupon1.getId()));
                    if (isValidCoupon){
                        currentMaximum+=validCoupon1.getDiscount();
                        purchaseNames.removeAll(getProductNames(validCoupon1.getId()));
                    }
                }
            }
            if (currentMaximum>maximumDiscount){
                maximumDiscount = currentMaximum;
            }
        }

        textDiscount.setText("" + String.format("%.2f",originalCost - maximumDiscount) + " after a discount of " + String.format("%.2f",maximumDiscount));

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