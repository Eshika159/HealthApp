package com.example.adity.healthcareapp2;

import java.util.Date;

/**
 * Created by adity on 17-02-2018.
 */

public class ChatMessage {

    private String messageText;
    private String messageUSer;
    private long messageTime;

    public ChatMessage(String messageText, String messageUSer) {
        this.messageText = messageText;
        this.messageUSer = messageUSer;
        messageTime = new Date().getTime();
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageUSer() {
        return messageUSer;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public ChatMessage(){


    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void setMessageUSer(String messageUSer) {
        this.messageUSer = messageUSer;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
