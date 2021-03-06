package com.babcock.umislite;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Profile implements Serializable {

    @SerializedName("StudentId")
    private int studentId;

    @SerializedName("DepartmentId")
    private int departmentId;

    @SerializedName("MatricNo")
    private String matricNumber;

    @SerializedName("FirstName")
    private String firstName;

    @SerializedName("MiddleName")
    private String middleName;

    @SerializedName("LastName")
    private String lastName;

    @SerializedName("Level")
    private String level;

    @SerializedName("Email")
    private String email;

    @SerializedName("MobileNumber")
    private String mobileNumber;

    @SerializedName("Address")
    private String address;

    @SerializedName("ProfilePic")
    private String profilePicture;

    @SerializedName("DeptCode")
    private String deptCode;

    @SerializedName("DeptName")
    private String deptName;

    @SerializedName("SchoolCode")
    private String schoolCode;

    @SerializedName("SchoolName")
    private String schoolName;

    @SerializedName("ProgramCode")
    private String programCode;

    @SerializedName("ProgramName")
    private String programName;

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
        return "Profile{" +
                "studentId=" + studentId +
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
