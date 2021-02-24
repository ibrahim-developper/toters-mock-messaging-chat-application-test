package com.example.toters_test_mockmessaging.UsersDB;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CurrentUser {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int currentUerMsgId;
    @ColumnInfo(name = "msgSendTo")
    public String msgSendTo;
    @ColumnInfo(name = "currentUserMsg")
    public String currentUserMsg;
    @ColumnInfo(name = "msgTime", defaultValue = "CURRENT_TIMESTAMP")
    public String msgTime;
}
