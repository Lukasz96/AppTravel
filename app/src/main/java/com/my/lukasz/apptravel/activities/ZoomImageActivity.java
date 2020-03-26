package com.my.lukasz.apptravel.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.my.lukasz.apptravel.R;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ZoomImageActivity extends Activity
{
    private Uri uri;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image);
        Intent intent=getIntent();
        String uriString=intent.getStringExtra("path");
        //Uri uri = Uri.parse(uriString);

        ImageView view = (ImageView) findViewById(R.id.zoomimage);



        uri=Uri.parse(uriString);



        Glide.with(this).load(uri).into(view);
        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(view);
        pAttacher.update();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (view.getDrawable() == null){
                    Toast.makeText(ZoomImageActivity.this,R.string.imageerrormessage,Toast.LENGTH_LONG).show();
                }
            }
        }, 1200);
    }


}