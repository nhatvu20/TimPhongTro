package com.example.timphongtro.Entity;

public class DistrictData {
    String name, img_district;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_district() {
        return img_district;
    }

    public void setImg_district(String img_district) {
        this.img_district = img_district;
    }

    public DistrictData(String name, String img_district) {
        this.name = name;
        this.img_district = img_district;
    }

    public DistrictData() {

    }
}
