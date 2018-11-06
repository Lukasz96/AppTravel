package com.example.lukasz.apptravel.activities;

import android.content.res.Configuration;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lukasz.apptravel.R;

public class ChooseTravelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_travel);

        ConstraintLayout constraintLayout= findViewById(R.id.choosetravelactivity);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            constraintLayout.setBackgroundResource(R.drawable.main_menu_background);
        }
        else {
            constraintLayout.setBackgroundResource(R.drawable.main_menu_background_landscape);
        }
    }
}
