package com.example.timphongtro.Database;

import android.net.Uri;

public class service_roomClass {
    private String name;
    private Uri img;

    public service_roomClass() {
    }

    public service_roomClass(String name, Uri img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getImg() {
        return img;
    }

    public void setImg(Uri img) {
        this.img = img;
    }
}
