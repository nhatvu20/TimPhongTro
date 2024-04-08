package com.example.timphongtro.Entity;

import com.example.timphongtro.Entity.Room;

import java.util.ArrayList;

public class User {
    private String email;

    private String uid;

    private String phone;

    private String name;

    public User() {
    }

    public User(String email, String uid, String name, String phone) {
        this.email = email;
        this.uid = uid;
        this.name = name;
        this.phone = phone;
    }

    public User(String email, String uid, String name) {
        this.email = email;
        this.uid = uid;
        this.name = name;

    }

    public String getphone() {
        return phone;
    }

    public void setphone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
