package com.example.truck_food.User;

import java.util.ArrayList;

public class Vendor extends User{
    String truckName;
    String bannerImage;
    String description;
    ArrayList<MenuItem> menu;

    public Vendor(){
        super();
    }

    public Vendor(String username, String email, String password, String truckName, String bannerImage, String description, ArrayList<MenuItem> menu) {
        super(username, email, password);
        this.truckName = truckName;
        this.bannerImage = bannerImage;
        this.description = description;
        this.menu = menu;
    }

    public String getTruckName() {
        return truckName;
    }

    public void setTruckName(String truckName) {
        this.truckName = truckName;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<MenuItem> menu) {
        this.menu = menu;
    }
}
