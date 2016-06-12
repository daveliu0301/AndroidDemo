package com.liu.dave.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.UUID;

/**
 * Created by LiuDong on 2016/6/12.
 */
public class LocalService extends Service {
    public class LocalBinder extends Binder {
        LocalService getService() {
            return LocalService.this;
        }
    }

    private final IBinder mIBinder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    public String getData() {
        return UUID.randomUUID().toString();
    }
}
