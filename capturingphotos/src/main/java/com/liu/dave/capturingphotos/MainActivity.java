package com.liu.dave.capturingphotos;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.liu.dave.capturingphotos.databinding.ActivityMainBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new MainViewModel(this);
        binding.setViewModel(viewModel);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            if (requestCode == MainViewModel.REQUEST_THUMBNAIL_CAPTURE) {
                Bitmap bm = getThumbnail(data);
                binding.picture.setImageBitmap(bm);

            } else if (requestCode == MainViewModel.REQUEST_IMAGE_CAPTURE) {
                galleryAddPic();
                setPic();
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MainViewModel.PERMISSION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "permission granted, try again.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "permission denied, can not take photo.", Toast.LENGTH_SHORT).show();

                }
                return;
        }
    }

    private Bitmap getThumbnail(Intent data) {
        Bundle extras = data.getExtras();
        Bitmap picture = (Bitmap) extras.get("data");
        return picture;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri photoUri = viewModel.getPhotoUri();
        mediaScanIntent.setData(photoUri);
        sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
        String photoPath = viewModel.getPhotoUri().getPath();

        int targetW = binding.picture.getWidth();
        int targetH = binding.picture.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(targetH / photoH, targetW / photoW);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, bmOptions);
        binding.picture.setImageBitmap(bitmap);
    }


}
