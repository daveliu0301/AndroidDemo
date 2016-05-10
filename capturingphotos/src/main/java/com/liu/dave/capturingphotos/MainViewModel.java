package com.liu.dave.capturingphotos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Observable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by LiuDong on 2016/5/9.
 */
public class MainViewModel {
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private Context context;

    public MainViewModel(Context context) {
        this.context = context;
    }

    public void requestCamera(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null)
            ((Activity) context).startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

}
