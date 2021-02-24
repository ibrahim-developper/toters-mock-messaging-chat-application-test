package com.example.toters_test_mockmessaging.Chat;

public class ChatLayoutModel {
    private String message;
    private String time;
    private boolean currentUser;

    public ChatLayoutModel(String message, String time, boolean currentUser) {
        this.message = message;
        this.time = time;
        this.currentUser = currentUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(boolean currentUser) {
        this.currentUser = currentUser;
    }
}

