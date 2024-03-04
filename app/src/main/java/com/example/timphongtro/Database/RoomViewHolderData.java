package com.example.timphongtro.Database;

public class RoomViewHolderData {
    String name, title_room;
    int price_room, people_room, area_room;
    Addresse addresse;

    public Addresse getAddresse() {
        return addresse;
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

