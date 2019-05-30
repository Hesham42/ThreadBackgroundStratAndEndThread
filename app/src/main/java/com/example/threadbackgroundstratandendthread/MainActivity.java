package com.example.threadbackgroundstratandendthread;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.example.threadbackgroundstratandendthread.Serv.ExampleJobService;
import com.example.threadbackgroundstratandendthread.Serv.MyBroadcastReceiver;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MyBroadcastReceiver MyReceiver;
    Intent mServiceIntent;
    private ExampleJobService mSensorService;

    Context ctx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        ctx = this;

        Log.wtf("Guinness",this.getPackageName());
        // Create an IntentFilter instance.
        IntentFilter intentFilter = new IntentFilter();


        // Add network connectivity change action.
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("com.pkg.perform.Ruby");
        // Set broadcast receiver priority.
        intentFilter.setPriority(100);

        MyReceiver = new MyBroadcastReceiver();
        mSensorService = new ExampleJobService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());

        if (intentFilter != null) {
            registerReceiver(MyReceiver, intentFilter);
             if (!isMyServiceRunning(mSensorService.getClass())) {
                startService(mServiceIntent);
            }
        }

    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");

                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (MyReceiver != null) {
            unregisterReceiver(MyReceiver);
            stopService(mServiceIntent);
        }
    }
    public Context getCtx() {
        return ctx;
    }


    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);
    }

}


