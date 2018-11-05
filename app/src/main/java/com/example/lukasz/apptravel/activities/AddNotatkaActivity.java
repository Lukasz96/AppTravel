package com.example.lukasz.apptravel.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.Notatka;

public class AddNotatkaActivity extends AppCompatActivity {

    private ImageView zdj;
    private AppDatabase mDb;
    private FloatingActionButton addphotobutton;
    public static final int YOUR_IMAGE_CODE = 1;
    private Uri uri=null;
    private long notatkaId;
    private long travelId;

    private EditText tyul;
    private EditText tresc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notatka);

        mDb=AppDatabase.getInstance(this);

        Intent intent=getIntent();
        notatkaId=intent.getLongExtra("notatkaId",0);
        travelId=intent.getLongExtra("travelId",0);

        //notatka=mDb.notatkaDao().getNotatkaById(notatkaId);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.newnotelabel);



        zdj=findViewById(R.id.addzdjecie);
        tyul=findViewById(R.id.addedittytulnotatki);
        tresc=findViewById(R.id.addedittrescnotatki);




        Glide.with(this).load(R.drawable.nophotoimage).into(zdj);
        zdj.setEnabled(false);


        addphotobutton=findViewById(R.id.addbuttonAddPhoto);

        addphotobutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "select a picture"), YOUR_IMAGE_CODE);
            }
        });
        zdj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddNotatkaActivity.this, ZoomImageActivity.class);
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
                this.grantUriPermission(this.getPackageName(),uri,Intent.FLAG_GRANT_READ_URI_PERMISSION);


                try {
                    Glide.with(this).load(uri).into(zdj);
                    zdj.setEnabled(true);
                    mDb.notatkaDao().updateZdjecieNotatkaById(notatkaId, uri.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(tyul.getText().toString().trim()==""){
            Toast.makeText(this,R.string.notitleofnote,Toast.LENGTH_LONG);
            return;
        }
        else {
            if(uri==null){
                mDb.notatkaDao().insertNotatka(new Notatka(0, null ,tyul.getText().toString().trim(),tresc.getText().toString(),travelId));
            }
            else  mDb.notatkaDao().insertNotatka(new Notatka(0, uri.toString(),tyul.getText().toString().trim(),tresc.getText().toString(),travelId));
            Intent intent = new Intent(AddNotatkaActivity.this, NotatkiListActivity.class);
            intent.putExtra("travelId", travelId);
            startActivity(intent);
            finish();
        }
    }
}
