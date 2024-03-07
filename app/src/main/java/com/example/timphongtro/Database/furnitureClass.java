package com.example.timphongtro.Database;


public class furnitureClass {
    private String name;
    private String img;

    public furnitureClass( ) {
    }

    public furnitureClass(String name, String img) {
        this.name = name;
        this.img = img;
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
}
