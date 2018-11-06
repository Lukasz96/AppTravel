package com.example.lukasz.apptravel.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.ListaDoSpakowania;
import com.example.lukasz.apptravel.db.entities.Podroz;
import com.example.lukasz.apptravel.imageCalc.BackgroundImageCalc;

import java.util.Date;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.button);
        final Button button2 = findViewById(R.id.button2);
        final Button button3 = findViewById(R.id.button3);
        ActionBar actionBar = getSupportActionBar();


        ConstraintLayout constraintLayout= findViewById(R.id.mainActivity);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            constraintLayout.setBackgroundResource(R.drawable.main_menu_background);
        }
        else {
            constraintLayout.setBackgroundResource(R.drawable.main_menu_background_landscape);
        }

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, CreateTravelActivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ChooseTravelActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.author:
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.authorlabel))
                        .setMessage(getString(R.string.authorinfo))
                        .setNeutralButton(getString(R.string.closelabel), null).show();
                return true;
            case R.id.credits:
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.credits))
                        .setMessage(getString(R.string.creditsinfo)+"\n"+getString(R.string.creditsinfotwo))
                        .setNeutralButton(getString(R.string.closelabel), null).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
