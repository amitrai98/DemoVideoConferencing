package com.example.amitrai.demovideoconferencing.modal;

import com.opentok.android.Subscriber;

/**
 * Created by amitrai on 4/10/16.
 */

public class Audiance {

    private String username;
    // Replace with your OpenTok API key
    private Subscriber subscriber;


    public Audiance( String username, Subscriber subscriber){
        this.username = username;
        this.subscriber = subscriber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }
}
