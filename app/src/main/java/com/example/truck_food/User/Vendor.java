package com.example.truck_food.User;

import java.io.Serializable;
import java.util.ArrayList;

public class Vendor extends User implements Serializable {
    String truckName;
    String bannerImage;
    String description;
    ArrayList<MenuItem> menu;
    double latitude;
    double longitude;

    public Vendor(){
        super();
    }

    public Vendor(String username, String email, String password, String truckName, String bannerImage, String description, ArrayList<MenuItem> menu) {
        super(username, email, password);
        this.truckName = truckName;
        this.bannerImage = bannerImage;
        this.description = description;
        this.menu = menu;
        latitude = 0;
        longitude = 0;
    }

    public Vendor(String username, String email, String password, String truckName, String bannerImage, String description, ArrayList<MenuItem> menu, double latitude, double longitude) {
        super(username, email, password);
        this.truckName = truckName;
        this.bannerImage = bannerImage;
        this.description = description;
        this.menu = menu;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "truckName='" + truckName + '\'' +
                ", bannerImage='" + bannerImage + '\'' +
                ", description='" + description + '\'' +
                ", menu=" + menu +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
