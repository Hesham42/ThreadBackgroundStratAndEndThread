package com.example.threadbackgroundstratandendthread.Serv;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.example.threadbackgroundstratandendthread.MainActivity;

import java.util.List;


public class MyBroadcastReceiver extends BroadcastReceiver {
    Intent mServiceIntent;
    private SensorService mSensorService;
    Context ctx;
    SharedPreferences prefs;

    @Override
    public void onReceive(Context context, Intent intent) {
        ctx = context;

        if (intent.getExtras() != null) {
            Boolean yourBool = intent.getExtras().getBoolean("Start");

            if (yourBool) {
                scheduleJob(context);
            } else {
                cancelJob(context);
            }

            if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
                Intent i = new Intent(context, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }

    public void scheduleJob(Context context) {
        mSensorService = new SensorService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
        JobServiceStart( true,context);
        Log.i("scheduleJob mSensorService.jobٍStart?",mSensorService.jobٍStart +"");

        if (!isMyServiceRunning(mSensorService.getClass())) {
            getCtx().startService(mServiceIntent);
        }


    }

    public void cancelJob(Context context) {
        JobServiceStart( false,context);
        mSensorService = new SensorService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
        Log.i("cancelJob mSensorService.jobٍStart?",mSensorService.jobٍStart +"");
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
                Log.i("isMyServiceRunning?", true + "");

                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }

    public Context getCtx() {
        return ctx;
    }

    public void JobServiceStart(boolean isRadioPlaying ,Context context) {
        try {
            Log.i("BroadCaast mSensorService.jobٍStart?",isRadioPlaying +"");
            SharedPreferences sharedPref = context.getSharedPreferences("ServiceRunning", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("jobٍStart", isRadioPlaying);
            editor.commit();
        } catch (NullPointerException e) {
            Log.e("Guinness", "error saving: are you testing?" + e.getMessage());

        }

    }

    private boolean isRadioRunning() {
        SharedPreferences sharedPref = getCtx().getSharedPreferences("ServiceRunning", Context.MODE_PRIVATE);
        return sharedPref.getBoolean("jobٍStart", false);
    }



}