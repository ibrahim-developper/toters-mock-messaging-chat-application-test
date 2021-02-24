package com.example.toters_test_mockmessaging.UsersDB;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User")
    List<User> getAllUsers();
    //help to insert an array of user class
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertListOfUser(List<User> usersList);

    @Query("SELECT userName,profileImage,message,createdTime FROM USER LEFT JOIN " +
            "(SELECT max(id) AS id,mUserName,message,createdTime  FROM UserMessages GROUP BY mUserName) " +
            "ON User.userName = mUserName ORDER BY createdTime DESC")
    List<UsersDisplay> getAllUsersDisplay();
}
