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
import android.view.View;
import android.widget.Button;
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
    Button toPackButton;
    Button shoppinglistButton;
    Button ridesButton;
    Button expensesButton;
    Button notesButton;
    Button travelStatsButton;
    long travelId;
    Date dateFrom;
    Date dateTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_main_menu);
        Intent intent=getIntent();
        travelId=intent.getLongExtra("travelId",0);
        mDb=AppDatabase.getInstance(getApplicationContext());
        podroz=mDb.podrozDao().getPodrozById(travelId);
        dateFrom=podroz.getDataOd();
        dateTo=podroz.getDataDo();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(podroz.getNazwa());
        actionBar.setSubtitle(dateFormat.format(dateFrom)+" - "+dateFormat.format(dateTo));
        toPackButton=findViewById(R.id.button4);
        shoppinglistButton=findViewById(R.id.button5);
        ridesButton=findViewById(R.id.button7);
        expensesButton=findViewById(R.id.button10);
        notesButton=findViewById(R.id.button8);
        travelStatsButton=findViewById(R.id.button9);

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

        toPackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mDb.listaDoSpakowaniaDao().getListaDoSpakowaniaByTravelId(podroz.getId())==null){
                    boolean areExistingPacklists;

                    if(mDb.listaDoSpakowaniaDao().getAllListyDoSpakowania().isEmpty()) areExistingPacklists=false;
                    else areExistingPacklists=true;
                    Intent intent=new Intent(TravelMainMenuActivity.this, NoExistingPacklistChioceActivity.class);
                    intent.putExtra("travelId",podroz.getId());
                    intent.putExtra("areExistingPacklists",areExistingPacklists);
                    startActivity(intent);
                }
                else {
                    Intent intent=new Intent(TravelMainMenuActivity.this, PackListActivity.class);
                    intent.putExtra("travelId",podroz.getId());
                    startActivity(intent);
                }
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menutravelmainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(TravelMainMenuActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
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
                        .setMessage(getString(R.string.deletetravelquestion))
                        .setIcon(getResources().getDrawable(R.drawable.deleteicon))
                        .setPositiveButton(getString(R.string.yeslabel), new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                mDb.podrozDao().deletePodrozById(travelId);
                                onBackPressed();
                                Toast.makeText(TravelMainMenuActivity.this, getString(R.string.deletedinfo), Toast.LENGTH_LONG).show();
                            }})
                        .setNegativeButton(getString(R.string.nolabel), null).show();
                return true;
            case R.id.editicon:
                Intent intent=new Intent(TravelMainMenuActivity.this, UpdateTravelActivity.class);
                intent.putExtra("travelIdToUpdate", travelId);
                intent.putExtra("nazwa",podroz.getNazwa());
                intent.putExtra("dataOd", dateFormat.format(dateFrom).toString());
                intent.putExtra("dataDo", dateFormat.format(dateTo).toString());
                intent.putExtra("budzet",podroz.getBudzet());
                startActivity(intent);

          //      Toast.makeText(TravelMainMenuActivity.this, "Edit", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
