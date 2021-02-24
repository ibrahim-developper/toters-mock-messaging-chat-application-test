package com.example.toters_test_mockmessaging.UsersDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = User.class,
        parentColumns = "userName",
        childColumns = "mUserName",
        onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = {"mUserName"})})
public class UserMessages {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "mUserName")
    public String mUserName;
    @ColumnInfo(name = "message")
    public String message;
    @ColumnInfo(name = "createdTime", defaultValue = "CURRENT_TIMESTAMP")
    public String createdTime;
}
