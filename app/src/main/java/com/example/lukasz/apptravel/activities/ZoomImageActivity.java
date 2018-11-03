package com.example.lukasz.apptravel.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import com.example.lukasz.apptravel.R;
import java.io.IOException;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ZoomImageActivity extends Activity
{


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image);
        Intent intent=getIntent();
        String uriString=intent.getStringExtra("path");
        Uri uri = Uri.parse(uriString);
        ImageView view = (ImageView) findViewById(R.id.zoomimage);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            view.setImageBitmap(bitmap);
        }catch (IOException e){
            e.printStackTrace();
        }

        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(view);
        pAttacher.update();
    }


}