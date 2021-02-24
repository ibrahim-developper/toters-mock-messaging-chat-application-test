package com.example.toters_test_mockmessaging.UsersDB;

public class UsersDisplay {
    private String userName;
    private byte[] profileImage;
    private String message;
    private String createdTime;

    public UsersDisplay(String userName, byte[] profileImage, String message, String createdTime) {
        this.userName = userName;
        this.profileImage = profileImage;
        this.message = message;
        this.createdTime = createdTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
