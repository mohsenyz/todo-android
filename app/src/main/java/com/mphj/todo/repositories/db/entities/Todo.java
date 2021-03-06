package com.mphj.todo.repositories.db.entities;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int localId;

    @ColumnInfo(name = "server_id")
    public int id;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "date")
    public long date;

    @ColumnInfo(name = "priority")
    public int priority;

    @ColumnInfo(name = "created_at")
    public long createdAt;

    @ColumnInfo(name = "updated_at")
    public long updatedAt;

    @ColumnInfo(name = "done")
    public boolean done;

    /**
     * Transient fields
     */

    @Ignore
    public List<Flag> flags;

    @Ignore
    public List<TodoTask> todoTasks;

    public Todo() {
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = this.createdAt;
    }

}
