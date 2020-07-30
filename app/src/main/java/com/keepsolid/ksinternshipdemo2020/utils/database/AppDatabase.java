package com.keepsolid.ksinternshipdemo2020.utils.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.keepsolid.ksinternshipdemo2020.model.GitRepoItem;

@Database(entities = {GitRepoItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RepoItemDao repoItemDao();
}
