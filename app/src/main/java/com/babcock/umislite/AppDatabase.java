package com.babcock.umislite;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {SchoolRepo.class, ProfileRepo.class, CoursesRepo.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RepoDao repoDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE CoursesRepo (CourseCode TEXT, CourseTitle TEXT, CourseType TEXT, CreditUnit TEXT, PRIMARY KEY(Id))");
        }
    };
}
