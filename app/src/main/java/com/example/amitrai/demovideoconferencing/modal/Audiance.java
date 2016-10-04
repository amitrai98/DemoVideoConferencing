package com.example.amitrai.demovideoconferencing.modal;

/**
 * Created by amitrai on 4/10/16.
 */

public class Audiance {

    private String username;
    // Replace with your OpenTok API key
    private String API_KEY;// = "45598312";
    // Replace with a generated Session ID
    private String SESSION_ID ;//= "2_MX40NTU5ODMxMn5-MTQ3MTM0NTM5NDg2MH5VRnl4RC8yODBYTCt1Y3dBQ0hrVEVHY0x-fg";
    // Replace with a generated token (from the dashboard or using an OpenTok server SDK)
    private String TOKEN ;//= "T1==cGFydG5lcl9pZD00NTU5ODMxMiZzaWc9NTlhODYyOWY4ZDNjNTlkMDVjNmZmNzEyZjRkMmI4N2QwYmM4YTdmOTpzZXNzaW9uX2lkPTJfTVg0ME5UVTVPRE14TW41LU1UUTNNVE0wTlRNNU5EZzJNSDVWUm5sNFJDOHlPREJZVEN0MVkzZEJRMGhyVkVWSFkweC1mZyZjcmVhdGVfdGltZT0xNDc1NDg5NDk0Jm5vbmNlPTAuMDgwOTE5OTY1NjM1OTg1MTQmcm9sZT1wdWJsaXNoZXImZXhwaXJlX3RpbWU9MTQ3ODA4MTQ5NA==";

    public Audiance(String username, String API_KEY,
                    String SESSION_ID, String TOKEN){

        this.username = username;
        this.API_KEY = API_KEY;
        this.SESSION_ID = SESSION_ID;
        this.TOKEN = TOKEN;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public void setAPI_KEY(String API_KEY) {
        this.API_KEY = API_KEY;
    }

    public String getSESSION_ID() {
        return SESSION_ID;
    }

    public void setSESSION_ID(String SESSION_ID) {
        this.SESSION_ID = SESSION_ID;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }
}
