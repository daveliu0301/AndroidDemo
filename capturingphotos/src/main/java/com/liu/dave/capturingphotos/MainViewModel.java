package com.liu.dave.capturingphotos;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LiuDong on 2016/5/9.
 */
public class MainViewModel {
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_THUMBNAIL_CAPTURE = 2;
    private Activity activity;
    private Uri photoUri;

    public MainViewModel(Activity activity) {
        this.activity = activity;
    }

    public void dispatchTakeThumbnailIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null)
            activity.startActivityForResult(takePictureIntent, REQUEST_THUMBNAIL_CAPTURE);
    }

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            File photo = null;
            try {
                photo = createImageFile();
            } catch (IOException e) {
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (photo != null) {
                photoUri = Uri.fromFile(photo);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        return image;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public static final int PERMISSION_REQUEST = 1;

    private void checkPermission(String permission) {
        int permissionCheck = ContextCompat.checkSelfPermission(activity, permission);
        if (PackageManager.PERMISSION_DENIED == permissionCheck) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                Toast.makeText(activity, "no \"" + permission + "\" permission", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, PERMISSION_REQUEST);
            }
        }

    }
}
