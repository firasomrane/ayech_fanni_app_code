package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ASUS on 28/11/2017.
 */

public class User implements Parcelable {

    private String user_id;
    private long phone_number;
    private String email;
    private String username;
    private Boolean est_createur;
    private long argent;

    public User(String user_id, long phone_number, String email, String username, Boolean est_createur, long argent) {
        this.user_id = user_id;
        this.phone_number = phone_number;
        this.email = email;
        this.username = username;
        this.est_createur = est_createur;
        this.argent = argent;
    }

    /*public User(String user_id, long phone_number, String email, String username, Boolean est_createur) {
        this.user_id = user_id;
        this.phone_number = phone_number;
        this.email = email;
        this.username = username;
        this.est_createur = est_createur;
    }*/


    public User() {

    }


    protected User(Parcel in) {
        user_id = in.readString();
        phone_number = in.readLong();
        email = in.readString();
        username = in.readString();
        }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public long getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(long phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getEst_createur() {
        return est_createur;
    }

    public void setEst_createur(Boolean est_createur) {
        this.est_createur = est_createur;
    }

    public long getArgent() {
        return argent;
    }

    public void setArgent(long argent) {
        this.argent = argent;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", est_createur='" + est_createur + '\'' +
                ", argent='" + argent + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeLong(phone_number);
        dest.writeString(email);
        dest.writeString(username);
    }
}
