package com.example.votingapp.data_type.user;

import java.util.ArrayList;

public class User {

    public String getUserId() {
        return userId;
    }

    private String userId;


    public User(String username, String email) {
        this.userId = Integer.toString(email.hashCode());
    }

}
