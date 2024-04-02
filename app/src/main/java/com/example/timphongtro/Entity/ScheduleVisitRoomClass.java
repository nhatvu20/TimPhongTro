package com.example.timphongtro.Entity;

public class ScheduleVisitRoomClass {
    private String name;
    private String phone;
    private String note;
    private String timeVisitRoom;

    private String idTo;
    private String idFrom;

    private String status; //0 la tao, 1 la chap nhan, 2 la tu choi

    private String idRoom;

    public ScheduleVisitRoomClass() {
    }

    public ScheduleVisitRoomClass(String name, String phone, String note, String timeVisitRoom, String idTo, String idFrom, String status, String idRoom) {
        this.idTo = idTo;
        this.idRoom = idRoom;
        this.status = status;
        this.idFrom = idFrom;
        this.name = name;
        this.phone = phone;
        this.note = note;
        this.timeVisitRoom = timeVisitRoom;
    }

    public String getIdTo() {
        return idTo;
    }

    public void setIdTo(String idTo) {
        this.idTo = idTo;
    }

    public String getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(String idFrom) {
        this.idFrom = idFrom;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
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
