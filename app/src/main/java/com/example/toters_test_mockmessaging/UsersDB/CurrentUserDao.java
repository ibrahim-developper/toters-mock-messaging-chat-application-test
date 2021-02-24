package com.example.toters_test_mockmessaging.UsersDB;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface CurrentUserDao {
    @Query("INSERT INTO CurrentUser (msgSendTo,currentUserMsg) VALUES(:arg0,:arg1)")
    void insertCurrentUserMsg(String arg0, String arg1);

    @Query("SELECT * FROM CurrentUser WHERE msgSendTo=:username ORDER BY currentUerMsgId ASC")
    List<CurrentUser> getCurrentUserChat(String username);
}
