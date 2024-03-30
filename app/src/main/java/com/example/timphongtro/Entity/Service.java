package com.example.timphongtro.Entity;

import com.google.gson.Gson;

public class Service {

    String title, img1, img2, description;
    int price, sold, amount;

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

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Service(String title, String img1, String img2, String description, int price, int sold, int amount) {
        this.title = title;
        this.img1 = img1;
        this.img2 = img2;
        this.description = description;
        this.price = price;
        this.sold = sold;
        this.amount = amount;
    }

    public Service(){

    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
