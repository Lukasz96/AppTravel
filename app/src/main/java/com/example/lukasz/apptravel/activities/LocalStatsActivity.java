package com.example.lukasz.apptravel.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.Przejazd;
import com.example.lukasz.apptravel.statsTools.PercentAxisValueFormatter;
import com.example.lukasz.apptravel.statsTools.PercentValueFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LocalStatsActivity extends AppCompatActivity {

    private long travelId;
    private AppDatabase mDb;
    private HorizontalBarChart wykresProcentBudzetu;
    private BarChart wykresPrzejazdynaDzien;
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

        ///////////  WYKRES BUDZETU ////////////////////////////////////////

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
        wykresProcentBudzetu.getAxisRight().setValueFormatter(new PercentAxisValueFormatter());
        wykresProcentBudzetu.getDescription().setEnabled(false);

        ArrayList<BarEntry> yVals =new ArrayList<>();
        yVals.add(new BarEntry(0f, getBudgetRatio()));

        BarDataSet set=new BarDataSet(yVals,getResources().getString(R.string.procentvaluelabel));
        set.setValueTextSize(16);
        set.setColor(Color.rgb(255, 227, 0));
        set.setValueFormatter(new PercentValueFormatter());
        wykresProcentBudzetu.getLegend().setTextSize(16);

        BarData data= new BarData(set);
        data.setBarWidth(0.45f);
        wykresProcentBudzetu.animateXY(700,700);
        wykresProcentBudzetu.setData(data);

        ///////////  WYKRES BUDZETU ////////////////////////////////////////

        //////////// PRZEJAZDY DANEGO DNIA ////////////////////////////////
        wykresPrzejazdynaDzien=findViewById(R.id.riderperdaybarchart);
        int iloscDniPOdrozy=getIloscDniPodrozy(mDb.podrozDao().getDateOdByTravelId(travelId),mDb.podrozDao().getDateDoByTravelId(travelId));
        int ilePodrozy=mDb.przejazdDao().getPrzejazdyDlaPodrozy(travelId).size();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(mDb.podrozDao().getDateOdByTravelId(travelId));

        List<Przejazd> przejazdList=mDb.przejazdDao().getPrzejazdyDlaPodrozy(travelId);
        for(int i=0; i<iloscDniPOdrozy;i++){

            

        }

    }

    private float getBudgetRatio(){

        double budzetPodrozy=mDb.podrozDao().getPodrozById(travelId).getBudzet();
        double sumaZakupow=mDb.elementListyDoSpakowaniaDao().getSumOfShoppingList(packListId, true);
        double sumaPrzejazdow=mDb.przejazdDao().getSumOfPrzejazdyByTravelId(travelId);
        double sumaWydatkow=mDb.wydatekDao().getSumOfWydatkiByTravelId(travelId);

        double sumaWszystkichWydatkow=sumaWydatkow+sumaPrzejazdow+sumaZakupow;

        float procentWydany=0;

        if(budzetPodrozy>0)  procentWydany=(float) (sumaWszystkichWydatkow/budzetPodrozy)*100;
        return Math.round(procentWydany);
    }


    public static int getIloscDniPodrozy(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        long dni=TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+1;
        System.out.println("DNIIIII "+dni);
        return (int)dni;
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
