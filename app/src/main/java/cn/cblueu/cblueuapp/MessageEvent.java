package cn.cblueu.cblueuapp;

public class MessageEvent {

    public final String message;
    public final String type;

    public MessageEvent(String type,String message) {
        this.type = type;
        this.message = message;
    }
}