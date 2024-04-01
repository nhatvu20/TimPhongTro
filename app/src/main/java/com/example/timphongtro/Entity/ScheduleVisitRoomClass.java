package com.example.timphongtro.Entity;

public class ScheduleVisitRoomClass {
    private String name;
    private String phone;
    private String note;
    private String timeVisitRoom;

    public ScheduleVisitRoomClass() {
    }

    public ScheduleVisitRoomClass(String name, String phone, String note, String timeVisitRoom) {
        this.name = name;
        this.phone = phone;
        this.note = note;
        this.timeVisitRoom = timeVisitRoom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTimeVisitRoom() {
        return timeVisitRoom;
    }

    public void setTimeVisitRoom(String timeVisitRoom) {
        this.timeVisitRoom = timeVisitRoom;
    }
}
