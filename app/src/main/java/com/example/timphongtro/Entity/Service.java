package com.example.timphongtro.Entity;

public class Service {

    String title, img;
    int price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Service(String title, String img, int price) {
        this.title = title;
        this.img = img;
        this.price = price;
    }

    public Service(){

    }
}
