package com.liu.dave.implicitintent;

import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by LiuDong on 2016/5/5.
 */
public class ShareActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_share);
        Uri uri = getIntent().getData();
        String type = getIntent().getType();
        // Figure out what to do based on the intent type
        if (type.indexOf("image/") != -1) {
            // Handle intents with image data ...
        } else if (type.equals("text/plain")) {
            // Handle intents with text ...
        }
    }
}
