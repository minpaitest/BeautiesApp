package com.android.beautiesapp.data.model;

import java.io.Serializable;

public class Product implements Serializable {

    String id;
    String name;
    String description;
    double price;
    String pic;


    public Product(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Product(String id, String name, String description, double price) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.pic = pic;
    }


}
