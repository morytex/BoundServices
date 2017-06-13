package com.example.moryta.boundservices;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Chronometer;

/**
 * Created by logonrm on 12/06/2017.
 */

public class BoundService extends Service {
    public static final String LOG_TAG = "BoundService";
    private IBinder binder;
    private Chronometer chronometer;

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new ServiceBinder(this);
        Log.v(LOG_TAG, "in onCreate");
        chronometer = new Chronometer(this);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(LOG_TAG, "in onBind");
        return binder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.v(LOG_TAG, "in onRebind");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(LOG_TAG, "in onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(LOG_TAG, "in onDestroy");
        chronometer.stop();
    }

    public String getTimestamp() {
        long elapsedMilis = SystemClock.elapsedRealtime()
                - chronometer.getBase();

        int hours = (int) (elapsedMilis / 3600000);
        int minutes = (int) (elapsedMilis - hours * 3600000) / 60000;
        int seconds = (int) (elapsedMilis - hours * 3600000 - minutes * 60000) / 1000;
        int milis = (int) (elapsedMilis - hours * 3600000 - minutes * 60000 - seconds * 1000);

        return hours + ":" + minutes + ":" + seconds + ":" +milis;
    }
}
