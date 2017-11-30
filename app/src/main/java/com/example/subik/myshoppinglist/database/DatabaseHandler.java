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

    private static final String TABLE_CUSTOMER = "CREATE TABLE "+
            TABLE_CUSTOMERS+" ( "+
            COLUMN_ID+" integer primary key not null, "+
            COLUMN_NAME+" text not null, "+
            COLUMN_USERNAME+" text unique not null );";

    SQLiteDatabase db;
    String query;
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

    public String[] login(String username){
        db = this.getReadableDatabase();
        query = "select name,id from "+TABLE_CUSTOMERS+" where "+COLUMN_USERNAME+" = ?";
        Cursor cursor = db.rawQuery(query,  new String[] { username });
        String [] result = new String[2];
        result [0] = "Not Found";
        if (cursor.moveToFirst()) {
            result [0] = cursor.getString(0);
            result [1] = cursor.getString(1);
        }
        return result;
    }

}



    //SQLiteDatabase sqLiteDatabase;
/*//Create table Login
    private static final String TABLE_CREATE = "create table login (id integer primary key not null ," +
            "name text not null, email text not null, password text not null);";*/


