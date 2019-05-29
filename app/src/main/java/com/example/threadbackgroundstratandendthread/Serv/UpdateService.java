package com.example.threadbackgroundstratandendthread.Serv;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;


public class UpdateService extends Service {

    Handler handler = new Handler();

    public UpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        handler.postDelayed(new Runnable() {
            public void run() {

                /*
                 * code will run every 15 minutes
                 */

                handler.postDelayed(this, 15 * 60 * 1000); //now is every 15 minutes
            }

        }, 100000);

        return START_STICKY;
    }
}