package com.example.lukasz.apptravel.activities;

import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.imageCalc.BackgroundImageCalc;

public class CreateTravelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_travel);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Nowa podróż");

        Display display = getWindowManager().getDefaultDisplay();
        ConstraintLayout constraintLayout= findViewById(R.id.createtravelactivity);
        int backgroundImageId=R.drawable.main_menu_background;
        BackgroundImageCalc backgroundImageCalc=new BackgroundImageCalc(this.getApplicationContext());
        Drawable backgroundImage=backgroundImageCalc.getCalculatedBackroundImage(display,backgroundImageId,
                400,600);
        constraintLayout.setBackground(backgroundImage);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
