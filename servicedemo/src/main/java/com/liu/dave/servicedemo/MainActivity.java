package com.liu.dave.servicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private LocalService mService;
    private boolean mBound;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBound = true;
            Log.i(TAG, "onServiceConnected");
            mService = ((LocalService.LocalBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
            Log.i(TAG, "onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //start a started service.
        //startService(new Intent(this, StartedService.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent bindServiceIntent = new Intent(this, LocalService.class);
        bindService(bindServiceIntent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            Log.i(TAG, "after unbindService");
            mBound = false;
        }
    }

    public void getMessage(View v) {
        if (mBound)
            Toast.makeText(this, mService.getData(), Toast.LENGTH_SHORT).show();
    }

}
