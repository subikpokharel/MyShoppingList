package com.example.subik.myshoppinglist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.subik.myshoppinglist.parsing.Customer;
import com.example.subik.myshoppinglist.parsing.Product;

import java.util.ArrayList;

/**
 * Created by subik on 11/29/17.
 */

public class DatabaseManager {

    private String query = "";
    private Cursor cursor;
    static DatabaseManager dbManager;
    static SQLiteDatabase db;
    static SQLiteDatabase database;

    private DatabaseManager(Context context) {
        createWritableDatabse(context);
        createReadableDatabse(context);

    }
    public static DatabaseManager getDatabaseManager(Context context) {
        if (dbManager == null) {
            dbManager = new DatabaseManager(context);
        }
        return dbManager;
    }
    private void createWritableDatabse(Context context) {
        db=new DatabaseHandler(context).getWritableDatabase();
    }

    private void createReadableDatabse(Context context) {
        database=new DatabaseHandler(context).getReadableDatabase();
    }

    public long signup(Customer customer) {
        ContentValues mValues = new ContentValues();
        query = "select * from tbl_customer";
        cursor = database.rawQuery(query, null);
        int count = cursor.getCount()+1;
        mValues.put(DatabaseHandler.COLUMN_ID, count);
        mValues.put(DatabaseHandler.COLUMN_NAME, customer.getName());
        mValues.put(DatabaseHandler.COLUMN_USERNAME, customer.getUsername());
        long rowInserted = db.insert(DatabaseHandler.TABLE_CUSTOMERS,null,mValues);
        cursor.close();
        return rowInserted;
    }

    public String[] login(String username){
        query = "select name,id from "+DatabaseHandler.TABLE_CUSTOMERS+" where "+DatabaseHandler.COLUMN_USERNAME+" = ?";
        cursor = database.rawQuery(query,  new String[] { username });
        String [] result = new String[2];
        result [0] = "Not Found";
        if (cursor.moveToFirst()) {
            result [0] = cursor.getString(0);
            result [1] = cursor.getString(1);
        }
        cursor.close();
        return result;
    }

    public long enterProduct(Product product){
        ContentValues mValues = new ContentValues();
        query = "select * from tbl_product";
        cursor = database.rawQuery(query, null);
        int count = cursor.getCount()+1;
        mValues.put(DatabaseHandler.COLUMN_PID, count);
        mValues.put(DatabaseHandler.COLUMN_PNAME, product.getProduct());
        mValues.put(DatabaseHandler.COLUMN_PPRICE, product.getPrice());
        long rowInserted = db.insert(DatabaseHandler.TABLE_PRODUCT, null, mValues);
        cursor.close();
        return rowInserted;
    }


    public ArrayList<Product> getEnteredProducts(){
        ArrayList<Product> items_products = new ArrayList<Product>();
        query = "SELECT * FROM "+DatabaseHandler.TABLE_PRODUCT;
        cursor = database.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                Product product = new Product();
                product.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_PID))));
                product.setProduct(cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_PNAME)));
                product.setPrice(cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_PPRICE)));
                items_products.add(product);
            }while (cursor.moveToNext());
        }
        return  items_products;
    }


}
