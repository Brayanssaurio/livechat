/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.appwebtest01;

/**
 *
 * @author vboxuser
 */
public class SendMessage {

    String fromData;
    String toData;
    String messageData;

    public String getFromData() {
        return fromData;
    }

    public void setFromData(String fromData) {
        this.fromData = fromData;
    }

    public String getToData() {
        return toData;
    }

    public void setToData(String toData) {
        this.toData = toData;
    }

    public String getMessageData() {
        return messageData;
    }

    public void setMessageData(String messageData) {
        this.messageData = messageData;
    }

    public SendMessage(String fromData, String toData, String messageData) {
        this.fromData = fromData;
        this.toData = toData;
        this.messageData = messageData;
    }
    
    public SendMessage() {
        this.fromData = "";
        this.toData = "";
        this.messageData = "";
    }

    
    
}
