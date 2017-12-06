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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subik.myshoppinglist.adapter.ProductAdapter;
import com.example.subik.myshoppinglist.database.DatabaseHandler;
import com.example.subik.myshoppinglist.database.DatabaseManager;
import com.example.subik.myshoppinglist.myapplication.MyApplication;
import com.example.subik.myshoppinglist.parsing.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListProductActivity extends AppCompatActivity {
    ListView lvProducts;
    ArrayList<Product> productArrayList;
    DatabaseManager databaseManager;
    MyApplication myApplication;
    //DatabaseHandler databaseHandler;// = new DatabaseHandler();
    Map<Integer,String> checkMap = new HashMap<>();
    Map<Integer,String> editMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        myApplication = (MyApplication) getApplication();
        databaseManager = DatabaseManager.getDatabaseManager(this);
        productArrayList = databaseManager.getEnteredProducts();
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
        checkMap = productAdapter.checkmap;
        editMap = productAdapter.editMap;
        /*for (int i =0; i<lvProducts.getChildCount(); i++){
            TextView hyper = lvProducts.getChildAt(i).findViewById(R.id.tvName);
            ListProductActivity.makeTextViewHyperlink(hyper);
        }*/

        //ListProductActivity.makeTextViewHyperlink(hyper);

    }


    public void updatePrice(View view) {
        //CheckBox checkBox;
        int dataUpdated = 0;
        //ListView listView = findViewById(R.id.listViewProduct);

        List<String> names = new ArrayList<>();
        List<String> price = new ArrayList<>();

        for (Map.Entry<Integer, String> entry : checkMap.entrySet()) {
            //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            names.add(entry.getValue());
        }

        for (Map.Entry<Integer, String> entry : editMap.entrySet()) {
            //System.out.println("Key = " + entry.getKey() + ", Price = " + entry.getValue());
            price.add(entry.getValue());
        }
        /*for (int i = 0; i < listView.getChildCount(); i++) {
            checkBox = listView.getChildAt(i).findViewById(R.id.chkBoxDashboad);
            if (checkBox.isChecked()){
                TextView product_name = (TextView) listView.getChildAt(i).findViewById(R.id.tvName);
                EditText product_price = (EditText) listView.getChildAt(i).findViewById(R.id.etPrice);
                databaseManager.updatePrice(product_name.getText().toString(),product_price.getText().toString());
                //Log.e("Updated: ", product_name.getText().toString());
                dataUpdated++;
            }

        }*/
        for (int i = 0; i<names.size(); i++){
            //Log.e("Name: ", names.get(i));
            //Log.e("Price: ", price.get(i));
            databaseManager.updatePrice(names.get(i),price.get(i));
            dataUpdated++;
        }
        Intent intent = new Intent(getApplicationContext(), ListProductActivity.class);
        startActivity(intent);
        finish();
        if (dataUpdated > 0)
            Toast.makeText(getApplicationContext(),"Data Successfully Updated...",Toast.LENGTH_LONG).show();
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


        if (id == R.id.nav_reset_system){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListProductActivity.this);
            // Setting Dialog Title
            alertDialog.setTitle("Confirm Reset...");

            // Setting Dialog Message
            alertDialog.setMessage("Are you sure you want to Reset the system ?");


            alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    final ProgressDialog progressDialog = new ProgressDialog(ListProductActivity.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    new android.os.Handler().postDelayed(new Runnable() {
                        public void run() {

                            //databaseHandler = new DatabaseHandler(getApplicationContext());
                            //databaseHandler.onUpgrade(databaseManager,1,2);
                            databaseManager.resetSystem();
                            Intent i = new Intent(getApplicationContext(), EnterProductActivity.class);
                            startActivity(i);
                            progressDialog.dismiss();
                        }
                    }, 1000);

                }
            });

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

    public static void makeTextViewHyperlink(TextView textView) {
        SpannableStringBuilder ssb = new SpannableStringBuilder( );
        ssb.append( textView.getText( ) );
        ssb.setSpan( new URLSpan("#"), 0, ssb.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
        textView.setText( ssb, TextView.BufferType.SPANNABLE );


    }
}
