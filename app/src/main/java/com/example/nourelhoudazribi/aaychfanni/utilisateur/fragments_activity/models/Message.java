package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models;

/**
 * Created by ASUS on 09/12/2017.
 */

public class Message {

    String profile_image;
    String message_text;
    String sender_id;
    String reciever_id;

    public Message(String profile_image, String message_text, String sender_id, String reciever_id) {
        this.profile_image = profile_image;
        this.message_text = message_text;
        this.sender_id = sender_id;
        this.reciever_id = reciever_id;
    }

    public Message() {
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReciever_id() {
        return reciever_id;
    }

    public void setReciever_id(String reciever_id) {
        this.reciever_id = reciever_id;
    }

    @Override
    public String toString() {
        return "Message{" +
                "profile_image='" + profile_image + '\'' +
                ", message_text='" + message_text + '\'' +
                ", sender_id='" + sender_id + '\'' +
                '}';
    }
}
