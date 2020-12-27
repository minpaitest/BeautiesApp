package com.android.beautiesapp.data.model;

import java.io.Serializable;

public class ProductCart implements Serializable {

    String id;
    String name;
    double price;
    int qty;
    double subTotal;
    double total;

    public ProductCart(){}

    public ProductCart(String id,String name, double price, int qty, double subTotal, double total) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.subTotal = subTotal;
        this.total = total;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
