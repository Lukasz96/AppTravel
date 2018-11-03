package com.example.lukasz.apptravel.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lukasz.apptravel.R;

import java.io.File;
import java.io.IOException;

public class NotatkiActivity extends AppCompatActivity {

    private ImageView zdj;
    private Button addphotobutton;
    public static final int YOUR_IMAGE_CODE = 1;
    private Uri uri;
    private long travelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notatki);

        Intent intent=getIntent();
        travelId=intent.getLongExtra("travelId",0);

        zdj=findViewById(R.id.zdjecie);
        addphotobutton=findViewById(R.id.buttonAddPhoto);

        addphotobutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "select a picture"), YOUR_IMAGE_CODE);
            }
        });
        zdj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(NotatkiActivity.this, ZoomImageActivity.class);

                intent.putExtra("path", uri.toString());

                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == YOUR_IMAGE_CODE) {
            if(resultCode == RESULT_OK && data!=null && data.getData()!=null) {
                 uri=data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    zdj.setImageBitmap(bitmap);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(NotatkiActivity.this, TravelMainMenuActivity.class);
        intent.putExtra("travelId", travelId);
        startActivity(intent);
        finish();
    }


}
