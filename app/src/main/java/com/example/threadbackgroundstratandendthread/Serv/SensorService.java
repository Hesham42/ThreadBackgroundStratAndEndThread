package com.example.threadbackgroundstratandendthread.Serv;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.threadbackgroundstratandendthread.MainActivity;
import com.example.threadbackgroundstratandendthread.Parsing.XMLParser;
import com.example.threadbackgroundstratandendthread.R;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.threadbackgroundstratandendthread.Serv.App.CHANEL_1_ID;

public class SensorService extends Service {
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
    public boolean jobٍStart = false;

    private static final String TAG = "Guinness";
    XMLParser xmlParser;
    ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
    public int counter = 0;
    private Timer timer;
    private TimerTask timerTask;

    public SensorService(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
    }

    public SensorService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if(isRadioRunning()){
            GetDataService();
            startTimer();
            return START_STICKY;
        }else {
            stoptimertask();
            return START_STICKY;
        }

    }

    public void GetDataService() {
        SharedPreferences sharedPref = this.getSharedPreferences("ServiceRunning", Context.MODE_PRIVATE);
        jobٍStart = sharedPref.getBoolean("jobٍStart", false);
        counter = sharedPref.getInt("counter", 0);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        Log.i("ondestroy mSensorService.jobٍStart?",isRadioRunning() +"");
        JobServiceStart(isRadioRunning());
        Intent broadcastIntent = new Intent(this, MyBroadcastReceiver.class);
        broadcastIntent.putExtra("Start", isRadioRunning());
        sendBroadcast(broadcastIntent);
        stoptimertask();

    }

    public void JobServiceStart(boolean isRadioPlaying) {
        try {
            Log.i("JobServiceStart mSensorService.jobٍStart?",isRadioPlaying +"");

            SharedPreferences sharedPref = this.getSharedPreferences("ServiceRunning", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("jobٍStart", isRadioPlaying);
            editor.putInt("counter", counter);
            editor.commit();
        } catch (NullPointerException e) {
            Log.e(TAG, "error saving: are you testing?" + e.getMessage());

        }

    }

    public void startTimer() {
        //set a new Timer

        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000); //
    }


    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  " + (counter++));
//                xmlParser = new XMLParser();
//                menuItems = xmlParser.ParsingData();
//                NotificationMenuItem(menuItems, getApplicationContext());

            }
        };
    }


    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    private boolean isRadioRunning() {
        SharedPreferences sharedPref = this.getSharedPreferences("ServiceRunning", Context.MODE_PRIVATE);
        return sharedPref.getBoolean("jobٍStart", false);
    }

    private void NotificationMenuItem(ArrayList<HashMap<String, String>> menuItems, Context context) {
        Log.wtf(TAG, menuItems.toString());
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

        for (int i = 0; i < menuItems.size(); i++) {
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

            managerCompat.notify(Integer.parseInt(menuItems.get(i).get(KEY_ID)), notification);

        }

    }

}