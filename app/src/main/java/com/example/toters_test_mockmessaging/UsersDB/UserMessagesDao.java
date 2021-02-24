package com.example.toters_test_mockmessaging.UsersDB;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface UserMessagesDao {

    @Query("INSERT INTO UserMessages (message,mUserName) VALUES(:arg0 , :arg1)")
    void insertUserMsg(String arg0 , String arg1);

    @Query("SELECT * FROM UserMessages WHERE mUserName=:arg0 AND createdTime=:arg1 ORDER BY id ASC")
    List<UserMessages> getUserMessagesAtTime(String arg0,String arg1);
}
