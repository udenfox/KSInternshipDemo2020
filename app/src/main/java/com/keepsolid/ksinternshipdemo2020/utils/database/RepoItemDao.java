package com.keepsolid.ksinternshipdemo2020.utils.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.keepsolid.ksinternshipdemo2020.model.GitRepoItem;

import java.util.List;

@Dao
public interface RepoItemDao {

    @Query("SELECT * FROM gitItemsTable")
    LiveData<List<GitRepoItem>> getAll();

    @Query("SELECT * FROM gitItemsTable WHERE id = :id")
    LiveData<GitRepoItem> getById(long id);

    @Insert
    void insert(GitRepoItem item);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<GitRepoItem> items);

    @Update
    void update(GitRepoItem item);

    @Delete
    void delete(GitRepoItem item);

    @Query("DELETE FROM gitItemsTable")
    void deleteAll();


}
