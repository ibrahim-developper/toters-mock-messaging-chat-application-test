package com.example.toters_test_mockmessaging.UsersDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, UserMessages.class, CurrentUser.class}, version = 3, exportSchema = false)
public abstract class UsersDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract UserMessagesDao userMessagesDao();

    public abstract CurrentUserDao currentUserDao();

    private static final String dbName = "chatdb";
    private static UsersDatabase Instance;

    public static UsersDatabase getInstance(Context context) {
        if (Instance == null) {
            Instance = Room.databaseBuilder(context.getApplicationContext(), UsersDatabase.class, dbName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return Instance;
    }
}
