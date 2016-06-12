package com.liu.dave.servicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by LiuDong on 2016/6/12.
 */
public class StartedService extends IntentService {
    public StartedService() {
        super(StartedService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Thread.sleep(5000);
            stopSelf();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "start service", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "stop service", Toast.LENGTH_SHORT).show();
    }
}
