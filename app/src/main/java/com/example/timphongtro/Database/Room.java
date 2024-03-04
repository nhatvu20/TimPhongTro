package com.example.timphongtro.Database;

public class Room {
    private String title_room;
    private int price_room;

    public Room() {
    }

    public Room(String title_room, int price_room) {
        this.title_room = title_room;
        this.price_room = price_room;
    }

    public String getTitle_room() {
        return title_room;
    }

    public void setTitle_room(String title_room) {
        this.title_room = title_room;
    }

    public int getPrice_room() {
        return price_room;
    }

    public void setPrice_room(int price_room) {
        this.price_room = price_room;
    }

    @Override
    public String toString() {
        return "Room{" +
                "title_room='" + title_room + '\'' +
                ", price_room=" + price_room +
                '}';
    }
}
