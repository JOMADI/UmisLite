package com.babcock.umislite.Discover;

import com.google.gson.annotations.SerializedName;

public class Facility {

    @SerializedName("facilityId")
    private int id;

    @SerializedName("facilityName")
    private String facilityName;

    @SerializedName("facilityAbout")
    private String facilityAbout;

    @SerializedName("facilityImage")
    private String facilityImage;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFacilityAbout() {
        return facilityAbout;
    }

    public void setFacilityAbout(String facilityAbout) {
        this.facilityAbout = facilityAbout;
    }

    public String getFacilityImage() {
        return facilityImage;
    }

    public void setFacilityImage(String facilityImage) {
        this.facilityImage = facilityImage;
    }

    @Override
    public String toString() {
        return "Facility{" +
                "id=" + id +
                ", facilityName='" + facilityName + '\'' +
                ", facilityAbout='" + facilityAbout + '\'' +
                ", facilityImage='" + facilityImage + '\'' +
                '}';
    }
}
