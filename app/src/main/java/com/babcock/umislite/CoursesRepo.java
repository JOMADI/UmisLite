package com.babcock.umislite;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CoursesRepo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int Id;

    @ColumnInfo(name = "CourseCode")
    private String courseCode;

    @ColumnInfo(name = "CourseTitle")
    private String courseTitle;

    @ColumnInfo(name = "CourseType")
    private String courseType;

    @ColumnInfo(name = "CreditUnit")
    private String creditUnit;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }


    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getCreditUnit() {
        return creditUnit;
    }

    public void setCreditUnit(String creditUnit) {
        this.creditUnit = creditUnit;
    }

    @Override
    public String toString() {
        return "CoursesRepo{" +
                "Id=" + Id +
                ", courseCode='" + courseCode + '\'' +
                ", courseTitle='" + courseTitle + '\'' +
                ", courseType='" + courseType + '\'' +
                ", creditUnit='" + creditUnit + '\'' +
                '}';
    }
}
