package com.example.patienttrackingfirebaserealtimedatabase;

public class NotificationModel {
    String id;
    String user_id;
    String isRead;
    String reason, date;

    public NotificationModel(String id, String user_id, String isRead, String reason, String date) {
        this.id = id;
        this.user_id = user_id;
        this.isRead = isRead;
        this.reason = reason;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String isRead() {
        return isRead;
    }

    public String getReason() {
        return reason;
    }

    public String getDate() {
        return date;
    }
}
