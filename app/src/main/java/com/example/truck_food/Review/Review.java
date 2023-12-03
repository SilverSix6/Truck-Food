package com.example.truck_food.Review;

import com.example.truck_food.User.Customer;

import java.util.ArrayList;

public class Review {
    String review;
    String customer;
    String vendor;
    int stars;
    ArrayList<String> items;

    Review(){
    }

    Review(String review, int stars, String customer, String vendor, ArrayList<String> items) {
        this.review = review;
        this.stars = stars;
        this.customer = customer;
        this.vendor = vendor;
        this.items = items;
    }
    public ArrayList<String> getItems() { return items;}
    public void setItems(ArrayList<String> items) { this.items = items; }
    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
