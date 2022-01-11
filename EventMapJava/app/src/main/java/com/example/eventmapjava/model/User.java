package com.example.eventmapjava.model;

import java.util.Random;

public class User {
    private String lastName;
    private String firstName;
    private int userID;

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getUserID() {
        return userID;
    }

    public void setCurrentUser(String lastName, String firstName, int userID) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.userID = userID;
    }
}
