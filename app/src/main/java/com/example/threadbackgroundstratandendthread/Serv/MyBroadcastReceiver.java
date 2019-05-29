package com.example.threadbackgroundstratandendthread.Serv;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;

import com.example.threadbackgroundstratandendthread.MainActivity;
import com.example.threadbackgroundstratandendthread.Parsing.XMLParser;
import com.example.threadbackgroundstratandendthread.R;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.JOB_SCHEDULER_SERVICE;
import static com.example.threadbackgroundstratandendthread.Serv.App.CHANEL_1_ID;


public class MyBroadcastReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {   Boolean yourBool = intent.getExtras().getBoolean("Start");

        if (yourBool){
            scheduleJob(context);
        }else {
            cancelJob(context);
        }
       }

    public void scheduleJob(Context context) {

        ComponentName componentName = new ComponentName(context, ExampleJobService.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler scheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d("Guinness", "Job scheduled");
        } else {
            Log.d("Guinness", "Job scheduling failed");
        }
    }

    public void cancelJob(Context context) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d("Guinness", "Job cancelled");
    }


}


