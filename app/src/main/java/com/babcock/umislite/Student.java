package com.babcock.umislite;

import com.google.gson.annotations.SerializedName;

public class Student {

    @SerializedName("StudentId")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                '}';
    }
}
