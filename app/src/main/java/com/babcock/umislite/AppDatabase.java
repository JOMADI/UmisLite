package com.babcock.umislite;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {SchoolRepo.class, ProfileRepo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RepoDao repoDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL(
//                    "CREATE TABLE ProfileRepo (userid TEXT, username TEXT, last_update INTEGER, PRIMARY KEY(Id))");
        }
    };
}
