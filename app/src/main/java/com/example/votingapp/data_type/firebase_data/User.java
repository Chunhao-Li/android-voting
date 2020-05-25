package com.example.votingapp.data_type.firebase_data;

import java.util.ArrayList;

public class User {

    private String username;
    private String email;

    public String getUserId() {
        return userId;
    }

    private String userId;

    private ArrayList<String> votingResultId = new ArrayList<>();


    public User() {
        // Default constructor for DataSnapshot.getValue(User.class)
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.userId = Integer.toString(email.hashCode());
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
