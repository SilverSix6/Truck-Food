package com.example.truck_food.User;

import com.example.truck_food.Image.Image;
import com.example.truck_food.Review.Review;

import java.io.Serializable;
import java.util.ArrayList;

public class Vendor extends User implements Serializable {
    String truckName;
    Image bannerImage;
    String description;
    ArrayList<MenuItem> menu;
    ArrayList<Review> reviews;
    double latitude;
    double longitude;


    public Vendor(){
        super();
    }

    public Vendor(String username, String email, String password, String truckName, Image bannerImage, String description, ArrayList<MenuItem> menu, ArrayList<Review> reviews) {
        super(username, email, password);

        this.truckName = truckName;
        this.bannerImage = bannerImage;
        this.description = description;
        this.menu = menu;
        latitude = 0;
        longitude = 0;
        this.reviews = reviews;
    }

    public Vendor(String username, String email, String password, String truckName, Image bannerImage, String description, ArrayList<MenuItem> menu, double latitude, double longitude, ArrayList<Review> reviews) {
        super(username, email, password);

        this.menu = menu;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reviews = reviews;
    }

    public String getTruckName() {
        return truckName;
    }

    public void setTruckName(String truckName) {
        this.truckName = truckName;
    }

    public Image getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(Image bannerImage) {
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

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
    }

    public double getAverageReview() {
        double sum = 0;
        if (reviews != null) {
            for(Review review: reviews) {
                sum += review.getStars();
            }

            return sum / (double)reviews.size();
        }
        return 0;
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
