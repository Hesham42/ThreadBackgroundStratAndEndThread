package com.example.threadbackgroundstratandendthread;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.threadbackgroundstratandendthread.Serv.SensorService;
import com.example.threadbackgroundstratandendthread.Serv.MyBroadcastReceiver;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MyBroadcastReceiver MyReceiver;
    Intent mServiceIntent;
    private SensorService mSensorService;
    Context ctx;
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        Log.wtf("Guinness", this.getPackageName());
        // Create an IntentFilter instance.
        IntentFilter intentFilter = new IntentFilter();

        ctx = this;

        MyReceiver = new MyBroadcastReceiver();
        intentFilter.addAction("com.pkg.perform.Ruby");
        if (intentFilter != null) {
            registerReceiver(MyReceiver, intentFilter);

            mSensorService = new SensorService(this);
            mServiceIntent = new Intent(this, mSensorService.getClass());

            if (isRadioRunning()) {

                if (!isMyServiceRunning(mSensorService.getClass())) {

                    startService(mServiceIntent);
                }
            }

        }

    }

    @Override
    protected void onDestroy() {
        if (MyReceiver != null) {
            unregisterReceiver(MyReceiver);
            stopService(mServiceIntent);
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        return;
    }

    public Context getCtx() {
        return ctx;
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }
    private boolean isRadioRunning() {
        SharedPreferences sharedPref = this.getSharedPreferences("ServiceRunning", Context.MODE_PRIVATE);
        return sharedPref.getBoolean("jobٍStart", false);
    }


}
