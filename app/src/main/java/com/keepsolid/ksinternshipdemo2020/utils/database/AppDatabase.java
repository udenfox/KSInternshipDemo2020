package com.keepsolid.ksinternshipdemo2020.utils.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.keepsolid.ksinternshipdemo2020.model.GitRepoItem;

@Database(entities = {GitRepoItem.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RepoItemDao repoItemDao();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE gitItemsTable ADD COLUMN userProfileUrl TEXT DEFAULT ''");
        }
    };

}


