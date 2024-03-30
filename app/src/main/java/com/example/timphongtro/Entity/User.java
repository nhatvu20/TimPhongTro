package com.example.timphongtro.Entity;

import com.example.timphongtro.Entity.Room;

import java.util.ArrayList;

public class User {
    private String email;

    private String uid;

    private String name;

    private ArrayList<Room> rooms;

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }


    public User(String email, String uid, String name, ArrayList<Room> rooms) {
        this.email = email;
        this.uid = uid;
        this.name = name;
        this.rooms = rooms;
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
