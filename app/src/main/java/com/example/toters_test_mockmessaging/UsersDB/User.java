package com.example.toters_test_mockmessaging.UsersDB;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @NonNull
    @PrimaryKey
    public String userName;
    @ColumnInfo(name = "profileImage", typeAffinity = ColumnInfo.BLOB)
    public byte[] profileImage;

    public User(@NonNull String userName, byte[] profileImage) {
        this.userName = userName;
        this.profileImage = profileImage;
    }
}
