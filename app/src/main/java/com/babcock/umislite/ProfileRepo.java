package com.babcock.umislite;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ProfileRepo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int Id;

    @ColumnInfo(name = "StudentId")
    private int studentId;

    @ColumnInfo(name = "DepartmentId")
    private int departmentId;

    @ColumnInfo(name = "MatricNo")
    private String matricNumber;

    @ColumnInfo(name = "FirstName")
    private String firstName;

    @ColumnInfo(name = "MiddleName")
    private String middleName;

    @ColumnInfo(name = "LastName")
    private String lastName;

    @ColumnInfo(name = "Level")
    private String level;

    @ColumnInfo(name = "Email")
    private String email;

    @ColumnInfo(name = "MobileNumber")
    private String mobileNumber;

    @ColumnInfo(name = "Address")
    private String address;

    @ColumnInfo(name = "ProfilePic")
    private String profilePicture;

    @ColumnInfo(name = "DeptCode")
    private String deptCode;

    @ColumnInfo(name = "DeptName")
    private String deptName;

    @ColumnInfo(name = "SchoolCode")
    private String schoolCode;

    @ColumnInfo(name = "SchoolName")
    private String schoolName;

    @ColumnInfo(name = "ProgramCode")
    private String programCode;

    @ColumnInfo(name = "ProgramName")
    private String programName;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getMatricNumber() {
        return matricNumber;
    }

    public void setMatricNumber(String matricNumber) {
        this.matricNumber = matricNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    @Override
    public String toString() {
        return "ProfileRepo{" +
                "Id=" + Id +
                ", studentId=" + studentId +
                ", departmentId=" + departmentId +
                ", matricNumber='" + matricNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", level='" + level + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", address='" + address + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", deptCode='" + deptCode + '\'' +
                ", deptName='" + deptName + '\'' +
                ", schoolCode='" + schoolCode + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", programCode='" + programCode + '\'' +
                ", programName='" + programName + '\'' +
                '}';
    }
}
