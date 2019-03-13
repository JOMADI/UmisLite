package com.babcock.umislite;

import com.babcock.umislite.Courses.Courses;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RepoDao {
    @Query("SELECT * FROM SchoolRepo")
    List<SchoolRepo> getAll();

    @Insert
    void insert(SchoolRepo schoolRepo);

    @Query("SELECT * FROM ProfileRepo")
    List<ProfileRepo> getProfile();

    @Insert
    void insert(ProfileRepo profileRepo);

    @Delete
    void delete(ProfileRepo profileRepo);

    @Query("SELECT * FROM CoursesRepo")
    List<CoursesRepo> getAllCourses();

    @Insert
    void insert(CoursesRepo coursesRepo);
}
