package com.my.lukasz.apptravel.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.my.lukasz.apptravel.R;
import com.my.lukasz.apptravel.db.AppDatabase;
import com.my.lukasz.apptravel.db.entities.Notatka;

public class EditNotatkiActivity extends AppCompatActivity {

    private ImageView zdj;
    private AppDatabase mDb;
    private FloatingActionButton addphotobutton;
    private FloatingActionButton deletephotobutton;
    public static final int YOUR_IMAGE_CODE = 1;
    private Uri uri;
    private long notatkaId;
    private long travelId;
    private Notatka notatka;
    private EditText tyul;
    private EditText tresc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notatki);

        mDb=AppDatabase.getInstance(this);

        Intent intent=getIntent();
        notatkaId=intent.getLongExtra("notatkaId",0);
        travelId=intent.getLongExtra("travelId",0);

        notatka=mDb.notatkaDao().getNotatkaById(notatkaId);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.editnotelabel);



        zdj=findViewById(R.id.zdjecie);
        tyul=findViewById(R.id.edittytulnotatki);
        tresc=findViewById(R.id.edittrescnotatki);

        tyul.setText(notatka.getTytul());
        tresc.setText(notatka.getTresc());

        if(notatka.getZdjecieUri()==null){
            zdj.setImageResource(R.drawable.nophotoimage);
            zdj.setEnabled(false);
        }

        else{
            uri=Uri.parse(notatka.getZdjecieUri());

                Glide.with(this).load(uri).into(zdj);
                zdj.setEnabled(true);





        }


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (zdj.getDrawable() == null){
                //    Toast.makeText(EditNotatkiActivity.this,R.string.imageerrormessage,Toast.LENGTH_LONG).show();
                    zdj.setImageResource(R.drawable.nophotoimage);
                    zdj.setEnabled(false);
                    uri=null;
                    mDb.notatkaDao().updateZdjecieNotatkaById(notatkaId, null);
                }
            }
        }, 1000);



        addphotobutton=findViewById(R.id.editbuttonAddPhoto);
        deletephotobutton=findViewById(R.id.editdeletephotobutton);

        tyul.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        tresc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        addphotobutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "select a picture"), YOUR_IMAGE_CODE);
            }
        });
        deletephotobutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                zdj.setImageResource(R.drawable.nophotoimage);
                zdj.setEnabled(false);
                uri=null;
                mDb.notatkaDao().updateZdjecieNotatkaById(notatkaId, null);
            }
        });
        zdj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(EditNotatkiActivity.this, ZoomImageActivity.class);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
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
                    Toast.makeText(this,R.string.errorloadingimage,Toast.LENGTH_LONG).show();
                }
            }
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.notatkimenu,menu);
        return super.onCreateOptionsMenu(menu);
    } */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                onBackPressed();
                return true;
            /*case R.id.savenotatka:
                if (tyul.getText().toString().trim() == "") {
                    Toast.makeText(this, R.string.notitleofnote, Toast.LENGTH_LONG).show();
                    return true;
                } else {
                    mDb.notatkaDao().updateNotatkaById(notatkaId, tyul.getText().toString(), tresc.getText().toString());
                    Intent intent = new Intent(EditNotatkiActivity.this, NotatkiListActivity.class);
                    intent.putExtra("travelId", travelId);
                    startActivity(intent);
                    finish();
                }
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }

    }




    @Override
    public void onBackPressed() {
        if (tyul.getText().toString().trim().equals("") || tyul.getText().toString()==null) {
            Toast.makeText(this, R.string.notitleofnote, Toast.LENGTH_LONG).show();
            return;
        } else {
            mDb.notatkaDao().updateNotatkaById(notatkaId, tyul.getText().toString(), tresc.getText().toString());
            Intent intent = new Intent(EditNotatkiActivity.this, NotatkiListActivity.class);
            intent.putExtra("travelId", travelId);
            startActivity(intent);
            finish();
        }

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
