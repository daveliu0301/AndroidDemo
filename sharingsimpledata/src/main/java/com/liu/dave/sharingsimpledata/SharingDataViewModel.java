package com.liu.dave.sharingsimpledata;

import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by LiuDong on 2016/5/6.
 */
public class SharingDataViewModel {
    private Context context;

    public SharingDataViewModel(Context context) {
        this.context = context;
    }

    public void shareTextWithChooser(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "text");
        context.startActivity(Intent.createChooser(sendIntent, "title"));
    }

}
