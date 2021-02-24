package com.example.toters_test_mockmessaging.RecyclerView;

import android.graphics.Bitmap;
import android.widget.TextView;

public class MainRecyclerViewItem {
    private Bitmap profileImage;
    private String userName, userMessage, msgTime;

    public MainRecyclerViewItem(Bitmap profileImage, String userName, String userMessage, String msgTime) {
        this.profileImage = profileImage;
        this.userName = userName;
        this.userMessage = userMessage;
        this.msgTime = msgTime;
    }

    public Bitmap getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Bitmap profileImage) {
        this.profileImage = profileImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }
}
