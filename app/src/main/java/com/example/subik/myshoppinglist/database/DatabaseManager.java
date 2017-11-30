package com.example.subik.myshoppinglist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.subik.myshoppinglist.parsing.Customer;

/**
 * Created by subik on 11/29/17.
 */

public class DatabaseManager {

    public static DatabaseManager dbManager;
    public static SQLiteDatabase db;

    private DatabaseManager(Context context) {
        createWritableDatabse(context);

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

    public long signup(Customer customer) {
        ContentValues mValues = new ContentValues();
        String query = "select * from tbl_customer";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        mValues.put(DatabaseHandler.COLUMN_ID, count);
        mValues.put(DatabaseHandler.COLUMN_NAME, customer.getName());
        mValues.put(DatabaseHandler.COLUMN_USERNAME, customer.getUsername());
        long rowInserted = db.insert(DatabaseHandler.TABLE_CUSTOMERS,null,mValues);
        return rowInserted;
    }


}
