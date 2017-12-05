package com.example.subik.myshoppinglist.parsing;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.subik.myshoppinglist.database.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by subik on 12/4/17.
 */

public class Coupon {
    private Integer id;
    private Double discount;
    private ArrayList<Product> productArrayList = new ArrayList<>();

    public ArrayList<Product> getProductArrayList() {
        return productArrayList;
    }

    public void setProductArrayList(ArrayList<Product> productArrayList) {
        this.productArrayList = productArrayList;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getId() {
        return id;
    }

    public Double getDiscount() {
        return discount;
    }



    public Coupon(int id, double discount, ArrayList<Product> products){
        this.id = id;
        this.discount = discount;
        this.productArrayList = (ArrayList<Product>) products.clone();
    }


    public void addProduct(Product product) {
        if(!productArrayList.contains(product)) {
            productArrayList.add(product);
        }
    }

    public void addProducts(ArrayList<Product> products){
        for (int i = 0; i < products.size(); i++) {
            addProduct(products.get(i));
        }
    }
    public ArrayList<Product> getProducts() {
        return productArrayList;
    }


    public static class DiscountResult{
        public double discount = 0;
        public double cost = 0;
        public double preCost = 0;
        public ArrayList<Coupon> coupons = new ArrayList<>();
    }

    /*public static DiscountResult generateLargestDiscount(ArrayList<Coupon> coupons){
        return generateLargestDiscount(coupons, -1.0);
    }*/



    /*public static DiscountResult generateLargestDiscount(ArrayList<Coupon> coupons, Double budget) {

        DiscountResult result = new DiscountResult();




        return  result;
    }


    private static Double getTotalCost(int Id){
        DatabaseManager.getDatabaseManager(Context);
    }*/














    /*


    public boolean conflicts(Coupon other) {
        for (Product product : other.productArrayList) {
            if(this.productArrayList.contains(product)){
                return true;
            }
        }
        return false;
    }

    public Coupon(double discount, ArrayList<Product> products){
        this.discount = discount;
        this.productArrayList = (ArrayList<Product>) products.clone();
    }

    public static DiscountResult generateLargestDiscount(ArrayList<Coupon> coupons, Double budget) {

        DiscountResult result = new DiscountResult();
        int[] curset = new int[coupons.size()];
        int[] bestset = new int[coupons.size()];
        for (int i = 0; i < curset.length; i++) {
            curset[i] = 0;
            bestset[i] = 0;
        }

        //run algorithm
        double bestDis = 0.00;
        double bestCost = 0.00;
        boolean done = false;
        while(updateFrom(curset,0)){
            int conflictIndex = getConflictsOn(curset,coupons);
            Log.d("curset: ",join(curset));
            while(conflictIndex >= 0) {
                if (!updateFrom(curset, conflictIndex)) {
                    done = true;
                    break;
                }
                conflictIndex = getConflictsOn(curset,coupons);
            }
            if(done)
                break;
            double[] costAndDis = getCostAndDiscount(curset,coupons);
            double cost   = costAndDis[0];
            double curDis = costAndDis[1];
            if((cost <= budget || budget < 0) && curDis > bestDis) {
                bestDis = curDis;
                bestCost = cost;
                for (int i = 0; i < curset.length; i++) {
                    bestset[i] = curset[i];
                }
            }
        }

        result.cost = bestCost;
        result.discount = bestDis;
        result.preCost = bestCost + bestDis;
        for (int i = 0; i < bestset.length; i++) {
            if(bestset[i] == 1)
                result.coupons.add(coupons.get(i));
        }
        return result;
    }*/


   /* private static boolean updateFrom(int[] arr, int index) {
        if(arr.length <= index)
            return false;
        while(arr[index] == 1){
            arr[index++] = 0;
            if(index >= arr.length)
                return false;
        }
        arr[index] = 1;
        return true;
    }

    private static int getConflictsOn(int[] set, ArrayList<Coupon> coupons){
        Coupon conflicter = new Coupon(0.0,new ArrayList<Product>());
        for(int i = set.length - 1; i >= 0; i--){
            if(set[i] == 1) {
                if (conflicter.conflicts(coupons.get(i))) {
                    return i;
                }
                else
                    conflicter.addProducts(coupons.get(i).getProducts());
            }
        }
        return -1;
    }


    private static double[] getCostAndDiscount(int[] set, ArrayList<Coupon> coupons){
        double cost = 0.00;
        double dis = 0.00;
        for (int i = 0; i < set.length; i++) {
            if(set[i] == 1) {
                dis += coupons.get(i).getDiscount();
                for (int j = 0; j < coupons.get(i).getProducts().size(); j++) {
                    cost += Double.parseDouble(coupons.get(i).getProducts().get(j).getPrice());
                }
            }
        }
        return new double[]{cost - dis, dis};
    }









    private static String join(int[] set){
        StringBuilder out = new StringBuilder();
        for (int i = set.length - 1; i >= 0; i--) {
            out.append(set[i]);
        }
        return out.toString();
    }*/
}
