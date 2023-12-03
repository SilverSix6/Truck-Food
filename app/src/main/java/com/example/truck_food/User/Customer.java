package com.example.truck_food.User;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer extends User implements Serializable {

    // Stores a list of vendor ids
    ArrayList<String> favorites;

    public Customer(){
        super();
    }
    public Customer(String username, String email, String password, ArrayList<String> favorites){
        super(username, email, password);
        this.favorites = favorites;
    }

    public ArrayList<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<String> favorites) {
        this.favorites = favorites;
    }

}
