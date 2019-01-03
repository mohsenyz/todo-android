package com.mphj.todo.repositories.db;

import com.mphj.todo.repositories.db.dao.FlagDao;
import com.mphj.todo.repositories.db.dao.TodoDao;
import com.mphj.todo.repositories.db.dao.UserListDao;
import com.mphj.todo.repositories.db.entities.Flag;
import com.mphj.todo.repositories.db.entities.Todo;
import com.mphj.todo.repositories.db.entities.TodoTask;
import com.mphj.todo.repositories.db.entities.UserList;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Todo.class, Flag.class, TodoTask.class, UserList.class}, version = 1)
public abstract class Db extends RoomDatabase {

    public abstract TodoDao todoDao();


    public abstract FlagDao flagDao();

    public abstract UserListDao userListDao();

}
