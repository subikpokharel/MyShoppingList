package com.example.subik.myshoppinglist.parsing;

/**
 * Created by subik on 11/30/17.
 */

public class Product {
    String product, price;
    Integer id;

    public Integer getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }

    public String getProduct() {
        return product;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
