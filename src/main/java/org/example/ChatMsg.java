package org.example;

public class ChatMsg {
    private String username;
    private String msg;
    public ChatMsg(String username, String msg) {
        this.username = username;
        this.msg = msg;
    }

    public ChatMsg() {}

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