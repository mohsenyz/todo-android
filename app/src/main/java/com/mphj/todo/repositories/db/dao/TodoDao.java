package com.mphj.todo.repositories.db.dao;


import com.mphj.todo.repositories.db.entities.Todo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TodoDao {

    @Insert
    void insert(Todo... todoList);

    @Update
    void update(Todo... todoList);

    @Delete
    void delete(Todo... repos);

    @Query("select * from todo")
    LiveData<List<Todo>> all();

}
