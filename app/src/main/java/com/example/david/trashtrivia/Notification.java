/*
Team Members: Rang Jin, Babatunji Olotu, David Garrett, Matt Chatiwat Lerdvongveerachai, and Abhi Dua
Group 9-Coffee Coffee Coffee
 */

package com.example.david.trashtrivia;

import java.sql.Timestamp;

/*
This class represents the notifications object
 */

public class Notification {
    private String id;  //object id in database
    private String senderId;    //id of the sender of the notification
    private String recipientId; //id of the recipient of the notification
    private String notificationText;    //text of the notification
    private Timestamp ts;   //timestamp of notification when it was created

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
