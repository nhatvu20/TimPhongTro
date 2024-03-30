package com.example.timphongtro.Entity;


public class FurnitureClass {
    private String name;
    private String img;
    private String id;

    public FurnitureClass() {
    }

    public FurnitureClass(String id, String name, String img) {
        this.id = id;
        this.name = name;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "FurnitureClass{" +
                "name='" + name + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
