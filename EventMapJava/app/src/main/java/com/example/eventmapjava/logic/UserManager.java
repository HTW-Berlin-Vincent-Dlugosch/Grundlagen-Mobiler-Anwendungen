package com.example.eventmapjava.logic;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.eventmapjava.model.User;

import java.util.Random;

public class UserManager implements UserManagerInterface{
    private User currentUser = new User();

    public User getCurrentUser() {
        return currentUser;
    }

    public void saveCurrentUser(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("firstName", currentUser.getFirstName());
        editor.putString("lastName", currentUser.getLastName());
        editor.putInt("userID", currentUser.getUserID());
        editor.apply();
    }

    public void loadCurrentUser(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        currentUser.setCurrentUser(sharedPref.getString("lastName", "Peter"), sharedPref.getString("firstName", "Zwegert"), sharedPref.getInt("userID", -1));
    }

    public boolean currentUserExists(Context context) {
        SharedPreferences sharedPrefs = context.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        return sharedPrefs.contains("userID");
    }

    public void createNewUser(String lastName, String firstName) {
        currentUser.setCurrentUser(lastName, firstName, new Random().nextInt(Integer.MAX_VALUE));
    }
}
