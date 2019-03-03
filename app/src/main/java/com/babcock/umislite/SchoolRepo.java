package com.babcock.umislite;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SchoolRepo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int Id;

    @ColumnInfo(name = "SchoolId")
    private int SchoolId;

    @ColumnInfo(name = "SchoolCode")
    private String schoolCode;

    @ColumnInfo(name = "SchoolName")
    private String schoolName;

    @ColumnInfo(name = "SchoolAbout")
    private String schoolAbout;

    @ColumnInfo(name = "schoolImage")
    private String schoolImaage;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(int schoolId) {
        SchoolId = schoolId;
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

    public String getSchoolImaage() {
        return schoolImaage;
    }

    public void setSchoolImaage(String schoolImaage) {
        this.schoolImaage = schoolImaage;
    }

    @Override
    public String toString() {
        return "SchoolRepo{" +
                "Id=" + Id +
                ", SchoolId=" + SchoolId +
                ", schoolCode='" + schoolCode + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", schoolAbout='" + schoolAbout + '\'' +
                ", schoolImaage='" + schoolImaage + '\'' +
                '}';
    }
}
