package com.example.subik.myshoppinglist.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.subik.myshoppinglist.adapter.ListCouponsAdapter;
import com.example.subik.myshoppinglist.parsing.Coupon;
import com.example.subik.myshoppinglist.parsing.Customer;
import com.example.subik.myshoppinglist.parsing.Product;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by subik on 11/29/17.
 */

public class DatabaseManager {

    private String query = "";
    private Cursor cursor;
    private static DatabaseManager dbManager;
    private static SQLiteDatabase db;
    private static SQLiteDatabase database;
    private long rowInserted;
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

    public long enterProduct(Product product){
        ContentValues mValues = new ContentValues();
       /* query = "select * from "+DatabaseHandler.TABLE_PRODUCT;
        cursor = database.rawQuery(query, null);
        int count = cursor.getCount()+1;
        mValues.put(DatabaseHandler.COLUMN_PID, count);*/
        mValues.put(DatabaseHandler.COLUMN_PNAME, product.getProduct());
        mValues.put(DatabaseHandler.COLUMN_PPRICE, product.getPrice());
        rowInserted = db.insert(DatabaseHandler.TABLE_PRODUCT, null, mValues);
        //cursor.close();
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
        cursor.close();
        return  items_products;
    }

    public void updatePrice(String name, String price){
        ContentValues mContentValues = new ContentValues();
        mContentValues.put(DatabaseHandler.COLUMN_PPRICE,price);
        mContentValues.put(DatabaseHandler.COLUMN_PNAME,name);
       db.update(DatabaseHandler.TABLE_PRODUCT, mContentValues,DatabaseHandler.COLUMN_PNAME + " = '" + name + "'", null);
    }


    public Integer getProductId(String name){
        query = "Select "+DatabaseHandler.COLUMN_PID+" From "+DatabaseHandler.TABLE_PRODUCT+" Where "+
                DatabaseHandler.COLUMN_PNAME+" = ?";
        cursor = database.rawQuery(query, new String[]{name});
        Integer id = 0;
        if (cursor.moveToFirst()) {
           String rst = cursor.getString(0);
            id = Integer.parseInt(rst);
        }
        cursor.close();
        return id;
    }


    public long insertCoupons(float discount){
        ContentValues mValues = new ContentValues();
       /* query = "select * from "+DatabaseHandler.TABLE_COUPONS;
        cursor = database.rawQuery(query, null);
        int count = cursor.getCount()+1;
        mValues.put(DatabaseHandler.COLUMN_CID, count);*/
        mValues.put(DatabaseHandler.COLUMN_DISCOUNT, discount);
        rowInserted = db.insert(DatabaseHandler.TABLE_COUPONS, null, mValues);
        //cursor.close();
        //Log.e("COUPON ID :", String.valueOf(rowInserted));
        return rowInserted;
    }


    public void insertCouponProducts(long id, ArrayList<Integer> ids){
        ContentValues mValues = new ContentValues();
        for (int i = 0; i<ids.size(); i++){
            /*query = "select * from "+DatabaseHandler.TABLE_COUPON_PRODUCTS;
            cursor = database.rawQuery(query, null);
            int count = cursor.getCount()+1;
            mValues.put(DatabaseHandler.COLUMN_CPID, count);*/
            mValues.put(DatabaseHandler.COLUMN_COUPONID, id);
            mValues.put(DatabaseHandler.COLUMN_PRODUCTID, ids.get(i));
            rowInserted = db.insert(DatabaseHandler.TABLE_COUPON_PRODUCTS, null, mValues);
            //Log.e("COUPONPRODUCT ID :", String.valueOf(rowInserted));
            //cursor.close();
        }

    }

    public ArrayList<Integer> getCouponIds(){
        ArrayList<Integer> items_ids = new ArrayList<>();
        query = "SELECT "+DatabaseHandler.COLUMN_CID+" FROM "+DatabaseHandler.TABLE_COUPONS;
        cursor = database.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                items_ids.add(cursor.getInt(0));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return  items_ids;
    }


    public void  deleteCoupon(Integer id){
        //db.delete(TABLE_BUS, KEY_BUS_NUM+"="+bus_num , null);
        db.delete(DatabaseHandler.TABLE_COUPONS,DatabaseHandler.COLUMN_CID+" = "+id ,null);
        //Log.e("Received ID: ", String.valueOf(id));
    }

    public float selectDiscount(Integer id){
        query = "SELECT "+DatabaseHandler.COLUMN_DISCOUNT+" FROM "+DatabaseHandler.TABLE_COUPONS+
                " WHERE "+DatabaseHandler.COLUMN_CID+" = ?";
        cursor =database.rawQuery(query, new String[]{id.toString()});
        float discount = 0;
        if (cursor.moveToFirst()){
            do {
                discount = (cursor.getFloat(0));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return  discount;
    }
//ArrayList<Product>
    public  ArrayList<Product>   selectCouponProductById(Integer id){

        ArrayList<Product> items_products = new ArrayList<Product>();

        query = " SELECT p."+DatabaseHandler.COLUMN_PID+", p."+DatabaseHandler.COLUMN_PNAME+", p."+DatabaseHandler.COLUMN_PPRICE+" FROM "+
                " "+DatabaseHandler.TABLE_PRODUCT+" p JOIN "+DatabaseHandler.TABLE_COUPON_PRODUCTS+" cp "+
                " ON cp."+DatabaseHandler.COLUMN_PRODUCTID+" = p."+DatabaseHandler.COLUMN_PID+" WHERE cp."+DatabaseHandler.COLUMN_COUPONID+
                " = ? ; ";
        //SELECT p.name, p.price FROM  TABLE tbl_product p JOIN tbl_coupon_products cp  ON cp.product_id = p.id WHERE cp.coupon_id = ?

        cursor =database.rawQuery(query, new String[]{id.toString()});
        //int i = 0;
        if (cursor.moveToFirst()){
            do {
                Product product = new Product();
                product.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_PID))));
                product.setProduct(cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_PNAME)));
                product.setPrice(cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_PPRICE)));
                items_products.add(product);
                //Log.e("TOTAL DATA: ", String.valueOf(product.getProduct()));
               // i++;
            }while (cursor.moveToNext());
        }
        //Log.e("TOTAL DATA: ", String.valueOf(i));
        cursor.close();
        return  items_products;
    }


    public ArrayList<Coupon> getAllCoupons(){

        LinkedHashMap<Integer,Coupon> couponMap = new LinkedHashMap<>();

        /*query = " SELECT c."+DatabaseHandler.COLUMN_CID+" , c."+DatabaseHandler.COLUMN_DISCOUNT+
                ", p."+DatabaseHandler.COLUMN_PID+", p."+DatabaseHandler.COLUMN_PNAME+" FROM "+DatabaseHandler.TABLE_COUPONS+
                " c JOIN "+DatabaseHandler.TABLE_COUPON_PRODUCTS+" cp ON c."+DatabaseHandler.COLUMN_CID+" = cp."+DatabaseHandler.COLUMN_COUPONID+
                " JOIN "+DatabaseHandler.TABLE_PRODUCT+" p ON p."+DatabaseHandler.COLUMN_PID+" = cp."+DatabaseHandler.COLUMN_PRODUCTID+
                " ORDER BY c."+DatabaseHandler.COLUMN_CID+" ASC";*/

        query = " SELECT c.*, p.* FROM "+DatabaseHandler.TABLE_COUPONS+
                " c JOIN "+DatabaseHandler.TABLE_COUPON_PRODUCTS+" cp ON c."+DatabaseHandler.COLUMN_CID+" = cp."+DatabaseHandler.COLUMN_COUPONID+
                " JOIN "+DatabaseHandler.TABLE_PRODUCT+" p ON p."+DatabaseHandler.COLUMN_PID+" = cp."+DatabaseHandler.COLUMN_PRODUCTID+
                " ORDER BY c."+DatabaseHandler.COLUMN_DISCOUNT+" DESC";

        cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do {
                    int coupon_id = cursor.getInt(0);
                    Coupon coupon = couponMap.get(coupon_id);
                    if (coupon == null){
                        coupon = new Coupon(coupon_id, cursor.getDouble(1),new ArrayList<Product>());
                        couponMap.put(coupon_id,coupon);
                    }
                    coupon.addProduct(new Product(cursor.getInt(2), cursor.getString(3),String.valueOf(cursor.getDouble(4))));
            }while (cursor.moveToNext());
        }
        //Log.e("Result: ", DatabaseUtils.dumpCursorToString(cursor));
        cursor.close();
       // database.close();


        return new ArrayList<>(couponMap.values());
        //database
    }


    public Product getProductByName(String s) {
        Product product = new Product();
        query = "Select * From "+DatabaseHandler.TABLE_PRODUCT+" Where "+
                DatabaseHandler.COLUMN_PNAME+" = ?";
        cursor = database.rawQuery(query, new String[]{s});
        if (cursor.moveToFirst()) {
            product.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_PID))));
            product.setProduct(cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_PNAME)));
            product.setPrice(cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_PPRICE)));
        }
        cursor.close();
        return product;
    }




    /*

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
    public static final String COLUMN_CPID = "id";
    public static final String COLUMN_COUPONID = "coupon_id";
    public static final String COLUMN_PRODUCTID = "product_id";
     */
}
