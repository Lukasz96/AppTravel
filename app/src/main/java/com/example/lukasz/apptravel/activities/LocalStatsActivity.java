package com.example.lukasz.apptravel.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.statsTools.WykresProcentowyBudzetu;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class LocalStatsActivity extends AppCompatActivity {

    private long travelId;
    private AppDatabase mDb;
    private HorizontalBarChart wykresProcentBudzetu;
    private long packListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_stats);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.statslabel);

        Intent intent=getIntent();
        mDb=AppDatabase.getInstance(this);
        travelId=intent.getLongExtra("travelId",0);
        packListId=mDb.listaDoSpakowaniaDao().getListaDoSpakowaniaByTravelId(travelId).getId();

        LimitLine limitLine=new LimitLine(100f,"100%");
        limitLine.setLineWidth(2f);
        limitLine.setTextSize(14);
        limitLine.setTextColor(Color.rgb(204,0,0));
        limitLine.setLineColor(Color.rgb(204,0,0));


        wykresProcentBudzetu = findViewById(R.id.wykresprocentbudzetu);

        wykresProcentBudzetu.getAxisLeft().setAxisMinimum(0); /// musi byc bo inaczej sie psuje
        wykresProcentBudzetu.getAxisLeft().setAxisMaximum(150);
        wykresProcentBudzetu.getAxisLeft().setTextColor(255255255);

        wykresProcentBudzetu.getXAxis().setEnabled(false);

        wykresProcentBudzetu.getAxisRight().setAxisMinimum(0);
        wykresProcentBudzetu.getAxisRight().setAxisMaximum(150);
        wykresProcentBudzetu.getAxisRight().addLimitLine(limitLine);
        wykresProcentBudzetu.getAxisRight().setTextSize(16);
        wykresProcentBudzetu.getDescription().setEnabled(false);

        ArrayList<BarEntry> yVals =new ArrayList<>();
        yVals.add(new BarEntry(0f, getBudgetRatio()));

        BarDataSet set=new BarDataSet(yVals,"Procent pokrycia budżetu");
        set.setValueTextSize(16);
        wykresProcentBudzetu.getLegend().setTextSize(16);
        BarData data= new BarData(set);
        data.setBarWidth(0.45f);
        wykresProcentBudzetu.animateXY(900,900);
        wykresProcentBudzetu.setData(data);


      //  wykresProcentBudzetu.animateXY(1500,1500);
       // wykresProcentBudzetu.invalidate();

    }

    private float getBudgetRatio(){

        double budzetPodrozy=mDb.podrozDao().getPodrozById(travelId).getBudzet();
        double sumaZakupow=mDb.elementListyDoSpakowaniaDao().getSumOfShoppingList(packListId, true);
        double sumaPrzejazdow=mDb.przejazdDao().getSumOfPrzejazdyByTravelId(travelId);
        double sumaWydatkow=mDb.wydatekDao().getSumOfWydatkiByTravelId(travelId);

        double sumaWszystkichWydatkow=sumaWydatkow+sumaPrzejazdow+sumaZakupow;

        float procentWydany=0;

        if(budzetPodrozy>0)  procentWydany=(float) (sumaWszystkichWydatkow/budzetPodrozy)*100;
        return procentWydany;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(LocalStatsActivity.this, TravelMainMenuActivity.class);
        intent.putExtra("travelId",travelId);
        startActivity(intent);
        finish();
    }

}
