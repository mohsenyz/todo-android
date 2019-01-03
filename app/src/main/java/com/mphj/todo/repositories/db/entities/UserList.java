package com.mphj.todo.repositories.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_list")
public class UserList {

    public UserList() {
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = this.createdAt;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int localId;

    @ColumnInfo(name = "server_id")
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "created_at")
    public long createdAt;

    @ColumnInfo(name = "updated_at")
    public long updatedAt;

}
