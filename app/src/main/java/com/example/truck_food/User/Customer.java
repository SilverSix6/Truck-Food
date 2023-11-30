package com.example.truck_food.User;

import java.util.ArrayList;

public class Customer extends User {

    // Stores a list of vendor ids
    ArrayList<String> favorites;

    public Customer(){
        super();
    }
    public Customer(String username, String email, String password){
        super(username, email, password);
        favorites = new ArrayList<>();
    }

    public ArrayList<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<String> favorites) {
        this.favorites = favorites;
    }

}
