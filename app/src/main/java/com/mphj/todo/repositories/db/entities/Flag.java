package com.mphj.todo.repositories.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo")
public class Flag {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "todo_id")
    public int todoId;

    @ColumnInfo(name = "flag")
    public String flag;
}
