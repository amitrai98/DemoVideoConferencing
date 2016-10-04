package com.example.amitrai.demovideoconferencing;
/**
 * Created by amit rai on 27/4/16.
 */
public interface ActivityListener {

    void onPauseActivity();
    void onResumeActivity();
    void onStoppedActivity();
    void onDestroyActivity();
    void onRequestAccessed();
}
