package com.mphj.todo.repositories.db.dao;

import com.mphj.todo.repositories.db.entities.Flag;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface FlagDao {

    @Insert
    void insert(Flag... todoList);

    @Insert
    void insert(List<Flag> todoList);

    @Update
    void update(Flag... todoList);

    @Delete
    void delete(Flag... repos);

}
