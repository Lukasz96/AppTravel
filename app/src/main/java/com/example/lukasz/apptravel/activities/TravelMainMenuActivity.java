package com.example.lukasz.apptravel.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.Podroz;
import com.example.lukasz.apptravel.imageCalc.BackgroundImageCalc;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TravelMainMenuActivity extends AppCompatActivity {

    Podroz podroz;
    AppDatabase mDb;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_main_menu);
        Intent intent=getIntent();
        long travelId=intent.getLongExtra("travelId",0);
        mDb=AppDatabase.getInstance(getApplicationContext());
        podroz=mDb.podrozDao().getPodrozById(travelId);
        Date dateFrom=podroz.getDataOd();
        Date dateTo=podroz.getDataDo();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(podroz.getNazwa());
        actionBar.setSubtitle(dateFormat.format(dateFrom)+" - "+dateFormat.format(dateTo));

        ////////////// USTAWIANIE TŁA
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            Display display = getWindowManager().getDefaultDisplay();
            ConstraintLayout constraintLayout= findViewById(R.id.travelmainmenuactivity);
            int backgroundImageId=R.drawable.main_menu_background_landscape;
            BackgroundImageCalc backgroundImageCalc=new BackgroundImageCalc(this.getApplicationContext());
            Drawable backgroundImage=backgroundImageCalc.getCalculatedBackroundImage(display,backgroundImageId,
                    600,400);
            constraintLayout.setBackground(backgroundImage);
        }
        else {
            Display display = getWindowManager().getDefaultDisplay();
            ConstraintLayout constraintLayout = findViewById(R.id.travelmainmenuactivity);
            int backgroundImageId = R.drawable.main_menu_background;
            BackgroundImageCalc backgroundImageCalc = new BackgroundImageCalc(this.getApplicationContext());
            Drawable backgroundImage = backgroundImageCalc.getCalculatedBackroundImage(display, backgroundImageId,
                    400, 600);
            constraintLayout.setBackground(backgroundImage);
        }
        ///////////////////////////////
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menutravelmainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(TravelMainMenuActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.deleteicon:
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.cautionlabel))
                        .setMessage(getString(R.string.deletequestion))
                        .setIcon(getResources().getDrawable(R.drawable.deleteicon))
                        .setPositiveButton(getString(R.string.yeslabel), new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(TravelMainMenuActivity.this, "Deleted", Toast.LENGTH_LONG).show();
                            }})
                        .setNegativeButton(getString(R.string.nolabel), null).show();
                return true;
            case R.id.editicon:
                Toast.makeText(TravelMainMenuActivity.this, "Edit", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
