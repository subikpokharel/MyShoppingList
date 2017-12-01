package com.example.subik.myshoppinglist;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.subik.myshoppinglist.adapter.ProductAdapter;
import com.example.subik.myshoppinglist.database.DatabaseManager;
import com.example.subik.myshoppinglist.myapplication.MyApplication;
import com.example.subik.myshoppinglist.parsing.Product;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    ListView lvProducts;
    ArrayList<Product> productArrayList;
    DatabaseManager databaseManager;
    MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        myApplication = (MyApplication) getApplication();
        databaseManager = DatabaseManager.getDatabaseManager(this);
        productArrayList = databaseManager.getEnteredProducts();
        //Toast.makeText(this,String.valueOf(productArrayList),Toast.LENGTH_LONG).show();
        bindData();

        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product product = productArrayList.get(i);
                String name = product.getProduct();
                Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
            }
        });

    }

    private void bindData() {
        lvProducts = findViewById(R.id.listViewProduct);
        ProductAdapter productAdapter = new ProductAdapter(this,R.layout.product_data,productArrayList);
        lvProducts.setAdapter(productAdapter);
    }

    public void updatePrice(View view){

        //ListAdapter productAdapter = lvProducts.getAdapter();
       /* ProductAdapter productAdapter = new ProductAdapter(this,R.layout.product_data,productArrayList);
        lvProducts.setAdapter(productAdapter);*/

       //ArrayList<Product> list =


        //Toast.makeText(this,String.valueOf(productAdapter.getItem(4)),Toast.LENGTH_LONG).show();
        //databaseManager
        //Toast.makeText(this,String.valueOf(productAdapter.getCount()),Toast.LENGTH_SHORT);
       /* for(int i =productAdapter.getCount(); i>0; i--){
            Product product = new Product();
            int id = product.getId();
            String str = product.getProduct();
            //Toast.makeText(this,String.valueOf(productAdapter.getItem(i)),Toast.LENGTH_SHORT).show();
            Toast.makeText(this,String.valueOf(id)+" "+str,Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_dashboard){
            Intent i = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(i);
        }
        if (id == R.id.nav_enterProduct){
            Intent i = new Intent(getApplicationContext(), EnterProductActivity.class);
            startActivity(i);
        }
        if (id == R.id.nav_enterCoupon){
            Intent i = new Intent(getApplicationContext(), CouponActivity.class);
            startActivity(i);
        }
        if (id == R.id.nav_logout) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Dashboard.this);
            // Setting Dialog Title
            alertDialog.setTitle("Confirm Logout...");

            // Setting Dialog Message
            alertDialog.setMessage("Are you sure you want to Logout?");

            alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    final ProgressDialog progressDialog = new ProgressDialog(Dashboard.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Logging Out...");
                    progressDialog.show();
                    new android.os.Handler().postDelayed(new Runnable() {
                        public void run() {

                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            myApplication.removeToken("Admin_Name");
                            startActivity(i);
                            finish();
                            progressDialog.dismiss();
                        }
                    }, 1000);

                }
            });

            // Setting Negative "NO" Btn
            alertDialog.setNegativeButton("Discard",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            // Showing Alert Dialog
            alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
