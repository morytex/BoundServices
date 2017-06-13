package com.example.moryta.boundservices;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BoundActivity extends AppCompatActivity {
    private BoundService boundService;
    private boolean isServiceBounded;
    private ServiceConnection serviceConnection;

    @BindView(R.id.tvTimestamp)
    TextView tvTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.v(BoundService.LOG_TAG, "activity - in onServiceConnected");
                ServiceBinder binder = (ServiceBinder) service;
                boundService = binder.getService();
                isServiceBounded = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.v(BoundService.LOG_TAG, "activity - in onServiceDisconnected");
                isServiceBounded = false;
            }
        };

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        Log.v(BoundService.LOG_TAG, "activity - in onStart");
        super.onStart();
        Intent intent = new Intent(this, BoundService.class);
        startService(intent);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        Log.v(BoundService.LOG_TAG, "activity - in onStop");
        super.onStop();
        this.unbindService();
    }

    @OnClick(R.id.btPrintTimestamp)
    public void onClickPrintTimestamp() {
        if (isServiceBounded) {
            tvTimestamp.setText(boundService.getTimestamp());
        }
    }

    @OnClick(R.id.btStop)
    public void onClickStop() {
        unbindService();

        Intent intent = new Intent(this, BoundService.class);
        stopService(intent);
    }

    private void unbindService() {
        if (isServiceBounded) {
            unbindService(serviceConnection);
            isServiceBounded = false;
        }
    }
}
