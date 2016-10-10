package com.example.amitrai.demovideoconferencing.modal;

/**
 * Created by amitrai on 29/9/16.
 */

public class UserBin {

    String username;

    String TOKEN;

    String SESSION_ID;

    public String getSESSION_ID() {
        return SESSION_ID;
    }

    public void setSESSION_ID(String SESSION_ID) {
        this.SESSION_ID = SESSION_ID;
    }

    public UserBin(String username, String TOKEN,  String SESSION_ID){
        this.username = username;
        this.TOKEN = TOKEN;
        this.SESSION_ID = SESSION_ID;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
