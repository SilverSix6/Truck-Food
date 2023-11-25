package com.example.truck_food.User;

public class Customer extends User {
    // Just some temporary stuff if we want to add customer specific data later
    String firstName;
    String lastName;

    public Customer(){
        super();
    }
    public Customer(String username, String email, String password, String firstName, String lastName){
        super(username, email, password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
