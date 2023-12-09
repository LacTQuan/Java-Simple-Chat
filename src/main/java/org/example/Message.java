package org.example;

public class Message {
    private String username;
    private String msg;
    public Message(String username, String msg) {
        this.username = username;
        this.msg = msg;
    }

    public Message(String message) {
        this.username = message.split(": ")[0];
        this.msg = message.split(": ")[1];
    }

    public Message() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
