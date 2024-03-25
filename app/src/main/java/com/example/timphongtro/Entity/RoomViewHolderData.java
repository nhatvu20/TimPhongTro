package com.example.timphongtro.Entity;

public class RoomViewHolderData {
    String name, title_room;
    int price_room, people_room, area_room;
    Address address;

    public Address getAddresse() {
        return address;
    }

    public String getName() {
        return name;
    }

    public int getPeople_room() {
        return people_room;
    }

    public int getPrice_room() {
        return price_room;
    }

    public int getArea_room() {
        return area_room;
    }

    public String getTitle_room() {
        return title_room;
    }
}

