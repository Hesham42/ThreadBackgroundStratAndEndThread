package com.example.threadbackgroundstratandendthread.Serv;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANEL_ex_ID = "ex";
    public  static  final String CHANEL_1_ID = "channel1";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new
                    NotificationChannel(
                    CHANEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("this is channel 1");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);


        }
    }

//    NotificationChannel servicenel = new
//            NotificationChannel(
//            CHANEL_ex_ID,
//            "Example Service Channel1 ",
//            NotificationManager.IMPORTANCE_DEFAULT);
//    NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(servicenel);

}


