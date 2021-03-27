package com.example.fbnativeadsrecyclerviewjava;


import android.app.Application;


public class MyAppliation extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the Audience Network SDK
//        AudienceNetworkAds.initialize(this);

         AudienceNetworkInitializeHelper.initialize(this);
    }

}