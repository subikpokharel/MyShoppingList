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


    //Table components for Products
    public static final String TABLE_PRODUCT = "tbl_product";
    public static final String COLUMN_PID = "id";
    public static final String COLUMN_PNAME = "name";
    public static final String COLUMN_PPRICE = "price";

    //Table components for Coupons
    public static final String TABLE_COUPONS = "tbl_coupon";
    public static final String COLUMN_CID = "id";
    public static final String COLUMN_DISCOUNT = "discount";

    //Table components for Coupons
    public static final String TABLE_COUPON_PRODUCTS = "tbl_coupon_products";
    //public static final String COLUMN_CPID = "id";
    public static final String COLUMN_COUPONID = "coupon_id";
    public static final String COLUMN_PRODUCTID = "product_id";



    private static final String CREATE_PRODUCT = "CREATE TABLE "+
            TABLE_PRODUCT+" ( "+
            COLUMN_PID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_PNAME+" TEXT UNIQUE NOT NULL, "+
            COLUMN_PPRICE+" REAL NOT NULL );";

    private static final String CREATE_COUPONS = "CREATE TABLE "+
            TABLE_COUPONS+" ( "+
            COLUMN_CID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_DISCOUNT+" REAL NOT NULL );";

    private static final String CREATE_COUPON_PRODUCTS = " CREATE TABLE "+
            TABLE_COUPON_PRODUCTS+" ( "+
            //COLUMN_CPID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_COUPONID+" INTEGER NOT NULL, "+
            COLUMN_PRODUCTID+" INTEGER  NOT NULL, "+
            "PRIMARY KEY("+COLUMN_COUPONID + "," + COLUMN_PRODUCTID + ")," +
            " FOREIGN KEY ( "+COLUMN_COUPONID+" ) REFERENCES "+TABLE_COUPONS+" ( "+COLUMN_CID+" ) "+
            " ON DELETE CASCADE, "+
            " FOREIGN KEY ( "+COLUMN_PRODUCTID+" ) REFERENCES "+TABLE_PRODUCT+" ( "+COLUMN_PID+" ) "+
            " ON DELETE CASCADE );";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PRODUCT);
        db.execSQL(CREATE_COUPONS);
        db.execSQL(CREATE_COUPON_PRODUCTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
            db.execSQL("DROP TABLE IF EXISTS " + CREATE_COUPONS);
            db.execSQL("DROP TABLE IF EXISTS " + CREATE_COUPON_PRODUCTS);
            onCreate(db);
        }
    }



}


