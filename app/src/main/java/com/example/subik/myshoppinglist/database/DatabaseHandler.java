package com.example.subik.myshoppinglist.database;

import android.content.Context;
import android.database.Cursor;
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

    //Table components for Products
    public static final String TABLE_PRODUCT = "tbl_product";
    public static final String COLUMN_PID = "id";
    public static final String COLUMN_PNAME = "name";
    public static final String COLUMN_PPRICE = "price";

    private static final String CREATE_CUSTOMER = "CREATE TABLE "+
            TABLE_CUSTOMERS+" ( "+
            COLUMN_ID+" integer primary key not null, "+
            COLUMN_NAME+" text not null, "+
            COLUMN_USERNAME+" text unique not null );";

    private static final String CREATE_PRODUCT = "CREATE TABLE "+
            TABLE_PRODUCT+" ( "+
            COLUMN_PID+" integer primary key not null, "+
            COLUMN_PNAME+" text unique not null, "+
            COLUMN_PPRICE+" Real not null );";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CUSTOMER);
        db.execSQL(CREATE_PRODUCT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
            onCreate(db);
        }
    }



}


