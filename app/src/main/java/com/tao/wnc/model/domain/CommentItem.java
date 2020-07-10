package com.tao.wnc.model.domain;

public class CommentItem {
    private String userName;
    private String message;
    private String timeStamp;

    public CommentItem(String userName, String message, String timeStamp) {
        this.userName = userName;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
