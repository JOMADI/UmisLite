package com.babcock.umislite.Courses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Courses implements Serializable {

    @SerializedName("CourseCode")
    private String courseCode;

    @SerializedName("CourseTitle")
    private String courseTitle;

    @SerializedName("CourseType")
    private String courseType;

    @SerializedName("CreditUnit")
    private String creditUnit;

    private boolean selected;


    public Courses(String courseCode, String courseTitle, String courseType, String creditUnit) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.courseType = courseType;
        this.creditUnit = creditUnit;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "courseCode='" + courseCode + '\'' +
                ", courseTitle='" + courseTitle + '\'' +
                ", courseType='" + courseType + '\'' +
                ", creditUnit='" + creditUnit + '\'' +
                ", selected=" + selected +
                '}';
    }
}
