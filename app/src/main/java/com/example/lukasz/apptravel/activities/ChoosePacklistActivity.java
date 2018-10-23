package com.example.lukasz.apptravel.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.imageCalc.BackgroundImageCalc;

public class ChoosePacklistActivity extends AppCompatActivity {

    private long travelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_packlist);
        Intent intent=getIntent();
        travelId=intent.getLongExtra("travelId",0);

        ////////////// USTAWIANIE TŁA
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            Display display = getWindowManager().getDefaultDisplay();
            ConstraintLayout constraintLayout= findViewById(R.id.choosepacklistactivity);
            int backgroundImageId=R.drawable.main_menu_background_landscape;
            BackgroundImageCalc backgroundImageCalc=new BackgroundImageCalc(this.getApplicationContext());
            Drawable backgroundImage=backgroundImageCalc.getCalculatedBackroundImage(display,backgroundImageId,
                    600,400);
            constraintLayout.setBackground(backgroundImage);
        }
        else {
            Display display = getWindowManager().getDefaultDisplay();
            ConstraintLayout constraintLayout = findViewById(R.id.choosepacklistactivity);
            int backgroundImageId = R.drawable.main_menu_background;
            BackgroundImageCalc backgroundImageCalc = new BackgroundImageCalc(this.getApplicationContext());
            Drawable backgroundImage = backgroundImageCalc.getCalculatedBackroundImage(display, backgroundImageId,
                    400, 600);
            constraintLayout.setBackground(backgroundImage);
        }
        ///////////////////////////////
    }
}
