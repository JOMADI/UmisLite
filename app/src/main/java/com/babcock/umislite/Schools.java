package com.babcock.umislite;

import com.google.gson.annotations.SerializedName;

public class Schools {

    @SerializedName("SchoolId")
    private int schoolId;

    @SerializedName("SchoolCode")
    private String schoolCode;

    @SerializedName("SchoolName")
    private String schoolName;

    @SerializedName("SchoolAbout")
    private String schoolAbout;

    @SerializedName("schoolImage")
    private String schoolImage;

    public Schools(int schoolId, String schoolCode, String schoolName, String schoolAbout, String schoolImage) {
        this.schoolId = schoolId;
        this.schoolCode = schoolCode;
        this.schoolName = schoolName;
        this.schoolAbout = schoolAbout;
        this.schoolImage = schoolImage;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolAbout() {
        return schoolAbout;
    }

    public void setSchoolAbout(String schoolAbout) {
        this.schoolAbout = schoolAbout;
    }

    public String getSchoolImage() {
        return schoolImage;
    }

    public void setSchoolImage(String schoolImage) {
        this.schoolImage = schoolImage;
    }

    @Override
    public String toString() {
        return "Schools{" +
                "schoolId=" + schoolId +
                ", schoolCode='" + schoolCode + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", schoolAbout='" + schoolAbout + '\'' +
                ", schoolImage='" + schoolImage + '\'' +
                '}';
    }
}
