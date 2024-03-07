package com.example.timphongtro.Database;

import java.util.ArrayList;

public class Room {

    private String id_room;
    private String title_room;
    private long price_room;
    private Addresse address;
    private String area_room;//Diện tích
    private long deposit_room;//tiền cọc
    private String description_room;
    private String gender_room;
    private int park_slot;
    private int person_in_room;//số người trong phòng

    private int status_room; //trạng thái phòng (0 phòng đang cho thuê, 1 phòng đã thuê và hết chỗ)

    //    private int time_post; //Thời gian đăng bài lấy bằng Date.now;
    private int type_room;
    private String phone;

    private ImagesClass images; //lưu ảnh và video cho bài viết
    private ArrayList<furnitureClass> furniture;

    private ArrayList<service_roomClass> services_room;

    public Room() {
    }

    public Room(String id_room, String title_room, long price_room, Addresse address, String area_room, long deposit_room, String description_room, String gender_room, int park_slot, int person_in_room, int status_room, int type_room, String phone, ImagesClass images, ArrayList<furnitureClass> furniture, ArrayList<service_roomClass> services_room) {
        this.id_room = id_room;
        this.title_room = title_room;
        this.price_room = price_room;
        this.address = address;
        this.area_room = area_room;
        this.deposit_room = deposit_room;
        this.description_room = description_room;
        this.gender_room = gender_room;
        this.park_slot = park_slot;
        this.person_in_room = person_in_room;
        this.status_room = status_room;
        this.type_room = type_room;
        this.phone = phone;
        this.images = images;
        this.furniture = furniture;
        this.services_room = services_room;
    }

    public String getId_room() {
        return id_room;
    }

    public void setId_room(String id_room) {
        this.id_room = id_room;
    }

    public String getTitle_room() {
        return title_room;
    }

    public void setTitle_room(String title_room) {
        this.title_room = title_room;
    }

    public long getPrice_room() {
        return price_room;
    }

    public void setPrice_room(long price_room) {
        this.price_room = price_room;
    }

    public Addresse getAddress() {
        return address;
    }

    public void setAddress(Addresse address) {
        this.address = address;
    }

    public String getArea_room() {
        return area_room;
    }

    public void setArea_room(String area_room) {
        this.area_room = area_room;
    }

    public long getDeposit_room() {
        return deposit_room;
    }

    public void setDeposit_room(long deposit_room) {
        this.deposit_room = deposit_room;
    }

    public String getDescription_room() {
        return description_room;
    }

    public void setDescription_room(String description_room) {
        this.description_room = description_room;
    }

    public String getGender_room() {
        return gender_room;
    }

    public void setGender_room(String gender_room) {
        this.gender_room = gender_room;
    }

    public int getPark_slot() {
        return park_slot;
    }

    public void setPark_slot(int park_slot) {
        this.park_slot = park_slot;
    }

    public int getPerson_in_room() {
        return person_in_room;
    }

    public void setPerson_in_room(int person_in_room) {
        this.person_in_room = person_in_room;
    }

    public int getStatus_room() {
        return status_room;
    }

    public void setStatus_room(int status_room) {
        this.status_room = status_room;
    }

    public int getType_room() {
        return type_room;
    }

    public void setType_room(int type_room) {
        this.type_room = type_room;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ImagesClass getImages() {
        return images;
    }

    public void setImages(ImagesClass images) {
        this.images = images;
    }

    public ArrayList<furnitureClass> getFurniture() {
        return furniture;
    }

    public void setFurniture(ArrayList<furnitureClass> furniture) {
        this.furniture = furniture;
    }

    public ArrayList<service_roomClass> getServices_room() {
        return services_room;
    }

    public void setServices_room(ArrayList<service_roomClass> services_room) {
        this.services_room = services_room;
    }
}
