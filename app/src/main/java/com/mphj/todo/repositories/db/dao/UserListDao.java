package com.mphj.todo.repositories.db.dao;

import com.mphj.todo.repositories.db.entities.Todo;
import com.mphj.todo.repositories.db.entities.UserList;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserListDao {

    @Query("select * from user_list where id = :id")
    LiveData<UserList> byId(int id);


    @Query("select * from user_list")
    LiveData<List<UserList>> all();

    @Insert
    void insert(UserList... userLists);

    @Update
    void update(UserList... userLists);

}
