package com.example.david.trashtrivia;

import java.sql.Timestamp;

public class Notification {
    private String id;
    private String senderId;
    private String recipientId;
    private String notificationText;
    private Timestamp ts;

    public Notification(String id, String senderId, String recipientId, String notificationText) {
        this.id = id;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.notificationText=notificationText;
        this.ts = new Timestamp(System.currentTimeMillis());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }
}
