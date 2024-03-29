package com.example.timphongtro.Entity;

import com.google.gson.Gson;

public class Service {

    String title, img, description;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public Service(String title, String img, String description, int price, int sold, int amount) {
        this.title = title;
        this.img = img;
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
