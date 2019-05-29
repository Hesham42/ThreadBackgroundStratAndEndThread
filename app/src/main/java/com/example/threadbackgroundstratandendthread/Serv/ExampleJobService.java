package com.example.threadbackgroundstratandendthread.Serv;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.threadbackgroundstratandendthread.MainActivity;
import com.example.threadbackgroundstratandendthread.Parsing.XMLParser;
import com.example.threadbackgroundstratandendthread.R;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.threadbackgroundstratandendthread.Serv.App.CHANEL_1_ID;

public class ExampleJobService extends JobService {
    private boolean jobCancelled = false;
    //app notifcation
    Intent activeIntent;
    PendingIntent pendingIntent;
    Notification notification;
    NotificationManagerCompat managerCompat;
    static final String KEY_ITEM = "item"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_NAME = "name";
    static final String KEY_COST = "cost";
    static final String KEY_DESC = "description";

    private static final String TAG = "Guinness";
    XMLParser xmlParser;
    ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");
            doBackgroundWork(params);

        return true;
    }

    private void doBackgroundWork(JobParameters params) {
        xmlParser = new XMLParser();
        menuItems = xmlParser.ParsingData();
        NotificationMenuItem(menuItems,getApplicationContext());
        jobFinished(params, false);

    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }


    private void NotificationMenuItem(ArrayList<HashMap<String, String>> menuItems , Context context) {
        Log.wtf(TAG,menuItems.toString());
        Intent activeIntent;
        PendingIntent pendingIntent;
        Notification notification;
        NotificationManagerCompat managerCompat;
        managerCompat = NotificationManagerCompat.from(context);
        activeIntent = new Intent(context, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(
                context,
                0,
                activeIntent,
                0);

        for (int i= 0; i<menuItems.size();i++){
            notification = new
                    NotificationCompat.Builder(context, CHANEL_1_ID)
                    .setSmallIcon(R.drawable.notifaction_24dp)
                    .setContentTitle(menuItems.get(i).get(KEY_NAME))
                    .setContentText(menuItems.get(i).get(KEY_DESC))
                    .setSubText(menuItems.get(i).get(KEY_COST))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();

            notification.flags = Notification.FLAG_AUTO_CANCEL;
            managerCompat.notify(Integer.parseInt(menuItems.get(i).get(KEY_ID)), notification);

        }

    }

}