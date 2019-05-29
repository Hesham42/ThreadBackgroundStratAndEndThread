package com.example.threadbackgroundstratandendthread;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

import com.example.threadbackgroundstratandendthread.Serv.MyBroadcastReceiver;

public class MainActivity extends AppCompatActivity {
    private MyBroadcastReceiver MyReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        MyReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter("com.pkg.perform.Ruby");
        if(intentFilter != null)
        {
            registerReceiver(MyReceiver, intentFilter);
        }
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(MyReceiver != null)
            unregisterReceiver(MyReceiver);
    }


}


