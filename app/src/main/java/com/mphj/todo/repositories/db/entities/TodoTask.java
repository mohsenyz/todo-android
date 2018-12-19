package com.mphj.todo.repositories.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo_task")
public class TodoTask {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "task")
    public String task;

    @ColumnInfo(name = "done")
    public boolean done;

}
