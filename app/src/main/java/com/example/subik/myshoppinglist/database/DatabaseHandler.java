package com.example.subik.myshoppinglist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by subik on 11/29/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "my_shopping_list.db";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Table components for Customer
    public static final String TABLE_CUSTOMERS = "tbl_customer";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_USERNAME = "username";

    private static final String TABLE_CUSTOMER = "CREATE TABLE "+
            TABLE_CUSTOMERS+" ( "+
            COLUMN_ID+" integer primary key not null, "+
            COLUMN_NAME+" text not null, "+
            COLUMN_USERNAME+" text unique not null );";

    //SQLiteDatabase db;
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CUSTOMER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            String query = "DROP TABLE IF EXISTS " + TABLE_CUSTOMERS;
            db.execSQL(query);
            onCreate(db);
        }
    }
}



    //SQLiteDatabase sqLiteDatabase;
/*//Create table Login
    private static final String TABLE_CREATE = "create table login (id integer primary key not null ," +
            "name text not null, email text not null, password text not null);";*/


