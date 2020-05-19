package com.example.votingapp.backend_storage;

public class User {

    public String username;
    public String email;


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
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
