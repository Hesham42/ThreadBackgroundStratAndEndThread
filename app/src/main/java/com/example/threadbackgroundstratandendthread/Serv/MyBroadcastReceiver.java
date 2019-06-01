package com.example.threadbackgroundstratandendthread.Serv;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;


public class MyBroadcastReceiver extends BroadcastReceiver
{
    Intent mServiceIntent;
    private SensorService mSensorService;
    Context ctx;
    SharedPreferences prefs;

    @Override
    public void onReceive(Context context, Intent intent)
    {   Boolean yourBool = intent.getExtras().getBoolean("Start");
        ctx = context;
        if (yourBool){
            scheduleJob(context);
        }else {
            cancelJob(context);
        }
    }

    public void scheduleJob(Context context) {
        mSensorService = new SensorService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
        mSensorService.JobServiceStart(true);
        if (!isMyServiceRunning(mSensorService.getClass())) {
            getCtx().startService(mServiceIntent);
        }


    }

    public void cancelJob(Context context) {
        mSensorService = new SensorService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
        mSensorService.JobServiceStart(false);
        mSensorService.stoptimertask();
        getCtx().stopService(mServiceIntent);
        if (isMyServiceRunning(mSensorService.getClass())) {
            getCtx().stopService(mServiceIntent);
        }


    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getCtx().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");

                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }

    public Context getCtx() {
        return ctx;
    }

}