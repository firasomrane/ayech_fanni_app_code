package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models;

/**
 * Created by ASUS on 01/12/2017.
 */

public class Like {

    private String user_id;

    public Like(String user_id) {
        this.user_id = user_id;
    }

    public Like() {

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Heart{" +
                "user_id='" + user_id + '\'' +
                '}';
    }
}