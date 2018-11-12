package com.example.lukasz.apptravel.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.Przejazd;
import com.example.lukasz.apptravel.statsTools.PercentAxisValueFormatter;
import com.example.lukasz.apptravel.statsTools.PercentValueFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LocalStatsActivity extends AppCompatActivity {

    private long travelId;
    private AppDatabase mDb;
    private TextView daneLiczbowe;
    private HorizontalBarChart wykresProcentBudzetu;
    private PieChart wykresRodzajeTransportu;
    private BarChart wykresWszystkieWydatki;
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


        //////////// DANE LICZBOWE ///////////////////////////////////////////

        daneLiczbowe=findViewById(R.id.numericaldane);

        daneLiczbowe.append(getResources().getString(R.string.traveldurationlabel)+" ");
        int iloscDni=getDifferenceDays(mDb.podrozDao().getDateOdByTravelId(travelId),mDb.podrozDao().getDateDoByTravelId(travelId));

        daneLiczbowe.append(String.valueOf(iloscDni)+" ");

        if (iloscDni>1) daneLiczbowe.append(getResources().getString(R.string.dayslabel));
        else daneLiczbowe.append(getResources().getString(R.string.daylabel));

        daneLiczbowe.append("\n\n");
        daneLiczbowe.append(getResources().getString(R.string.averagecostlabel));


        double sumaZakupow=mDb.elementListyDoSpakowaniaDao().getSumOfShoppingList(packListId, true,true,"PLN");
        double sumaPrzejazdow=mDb.przejazdDao().getSumOfPrzejazdyByTravelId(travelId,"PLN");
        double sumaWydatkow=mDb.wydatekDao().getSumOfWydatkiByTravelId(travelId,"PLN");

        double sumaWszystkichWydatkow=sumaWydatkow+sumaPrzejazdow+sumaZakupow;
        double averageCost=sumaWszystkichWydatkow/iloscDni;
        String average = String.format("%.2f", averageCost);
        daneLiczbowe.append(average);
        daneLiczbowe.append("\n\n");
        daneLiczbowe.append(getResources().getString(R.string.numberofrideslabel)+" ");
        int przejazdy= mDb.przejazdDao().getPrzejazdyDlPodrozy(travelId).size();
        daneLiczbowe.append(String.valueOf(przejazdy)+" ");
        if(przejazdy>1) daneLiczbowe.append(getResources().getString(R.string.ridesmanylabel));
        else daneLiczbowe.append(getResources().getString(R.string.ridelabel));
        daneLiczbowe.append("\n\n");
        daneLiczbowe.append(getResources().getString(R.string.listtopacklabel)+ " ");

        int packListItems=mDb.elementListyDoSpakowaniaDao().getElementyZDanejListyCzyDoSpakowania(packListId,true).size();
        daneLiczbowe.append(String.valueOf(packListItems)+" ");

        if (packListItems>0) daneLiczbowe.append(getResources().getString(R.string.thingslabel));
        else daneLiczbowe.append(getResources().getString(R.string.thinglabel));


        daneLiczbowe.append(getResources().getString(R.string.youpackedlabel)+" ");
        int spakowane = mDb.elementListyDoSpakowaniaDao().getSpakowaneElementy(packListId,true,true).size();
        daneLiczbowe.append(String.valueOf(spakowane));




        //////////// DANE LICZBOWE ///////////////////////////////////////////

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

        //////////// ILE PIENIĘDZY NA DANY RODZAJ TRANSPOTU ////////////////////////////////
        wykresRodzajeTransportu=findViewById(R.id.wykresrodzajetransportu);
        wykresRodzajeTransportu.setUsePercentValues(true);
        wykresRodzajeTransportu.setDrawHoleEnabled(true);
        wykresRodzajeTransportu.getDescription().setEnabled(false);
       // wykresRodzajeTransportu.setHoleColor(255255255);
        wykresRodzajeTransportu.setTransparentCircleRadius(55f);
        wykresRodzajeTransportu.setDragDecelerationFrictionCoef(0.95f);
        wykresRodzajeTransportu.getLegend().setTextSize(19);
        //wykresRodzajeTransportu.setExtraOffsets(5,10,5,5);

        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues=getTransportData();
        PieDataSet dataSet = new PieDataSet(yValues,"");


        dataSet.setColors(new int[]{Color.parseColor("#248f24"), Color.parseColor("#0099ff")
        , Color.parseColor("#cc3300"),Color.parseColor("#e68a00"), Color.parseColor("#669999")});
        dataSet.setValueFormatter(new PercentValueFormatter());
        dataSet.setValueTextColor(Color.rgb(255, 255, 255));
        dataSet.setValueTextSize(16);
        PieData data1=new PieData(dataSet);
        data1.setValueTextSize(16f);

        wykresRodzajeTransportu.setData(data1);
        wykresRodzajeTransportu.animateX(800);

        //////////// ILE PIENIĘDZY NA DANY RODZAJ TRANSPOTU ////////////////////////////////

        //////////// ILE PIENIĘDZY NA JAKIE WYDATKI /////////////////////////////////
        wykresWszystkieWydatki=findViewById(R.id.wykreswydatki);
        float wydatkiNaZakupy=(float)Math.round(mDb.elementListyDoSpakowaniaDao().getSumOfShoppingList(packListId,true,true,"PLN"));
        float wydatkiNaNocleg=(float)Math.round(mDb.wydatekDao().getSumOfWydatkiByTravelIdAndCategory(travelId,1,"PLN"));
        float wydatkiNaJedzenie=(float)Math.round(mDb.wydatekDao().getSumOfWydatkiByTravelIdAndCategory(travelId,2,"PLN"));
        float wydatkiNaZwiedzanie=(float)Math.round(mDb.wydatekDao().getSumOfWydatkiByTravelIdAndCategory(travelId,3,"PLN"));
        float wydatkiInne=(float)Math.round(mDb.wydatekDao().getSumOfWydatkiByTravelIdAndCategory(travelId,4,"PLN"));
        float wydatkiTransport=(float)Math.round(mDb.przejazdDao().getSumOfPrzejazdyByTravelId(travelId,"PLN"));

        ArrayList<BarEntry> entryArrayList= new ArrayList<>();
          entryArrayList.add(new BarEntry(0,wydatkiNaZakupy));
          entryArrayList.add(new BarEntry(1,wydatkiNaNocleg));
          entryArrayList.add(new BarEntry(2,wydatkiNaJedzenie));
          entryArrayList.add(new BarEntry(3,wydatkiNaZwiedzanie));
          entryArrayList.add(new BarEntry(4,wydatkiTransport));
          entryArrayList.add(new BarEntry(5,wydatkiInne));

        BarDataSet set1= new BarDataSet(entryArrayList,"");

        set1.setColors(new int[]{Color.parseColor("#ffcc66"), Color.parseColor("#ccccff")
                , Color.parseColor("#66ff99"),Color.parseColor("#cccc00"), Color.parseColor("#e67300"),
                Color.parseColor("#cc6699")});

        set1.setDrawValues(true);
        set1.setValueTextSize(16);
        BarData barData= new BarData(set1);

        LegendEntry zakupy = new LegendEntry();
        zakupy.label = getResources().getString(R.string.shoppinglistlabel);
        zakupy.formColor = Color.parseColor("#ffcc66");

        LegendEntry noclegi = new LegendEntry();
        noclegi.label = getResources().getString(R.string.accomodationlabel);
        noclegi.formColor = Color.parseColor("#ccccff");

        LegendEntry jedzenie = new LegendEntry();
        jedzenie.label = getResources().getString(R.string.foodlabel);
        jedzenie.formColor = Color.parseColor("#66ff99");

        LegendEntry zwiedzanie = new LegendEntry();
        zwiedzanie.label = getResources().getString(R.string.sightseeinglabel);
        zwiedzanie.formColor = Color.parseColor("#cccc00");

        LegendEntry transport = new LegendEntry();
        transport.label = getResources().getString(R.string.ridesbuttonlabel);
        transport.formColor = Color.parseColor("#e67300");

        LegendEntry inne = new LegendEntry();
        inne.label = getResources().getString(R.string.otherlabel);
        inne.formColor = Color.parseColor("#cc6699");

        wykresWszystkieWydatki.getAxisRight().setEnabled(false);
        wykresWszystkieWydatki.getLegend().setCustom(Arrays.asList(zakupy,noclegi,jedzenie,zwiedzanie,transport,inne));
        wykresWszystkieWydatki.getLegend().setWordWrapEnabled(true);
        wykresWszystkieWydatki.getDescription().setEnabled(false);
        wykresWszystkieWydatki.getXAxis().setDrawLabels(false);
        wykresWszystkieWydatki.getLegend().setTextSize(19);
        wykresWszystkieWydatki.getAxisLeft().setTextSize(16);
        wykresWszystkieWydatki.animateXY(650,650);
        wykresWszystkieWydatki.setData(barData);


    }

    private ArrayList<PieEntry> getTransportData(){

        ArrayList<PieEntry> values = new ArrayList<>();

        float cenyAut=(float)mDb.przejazdDao().getSumKosztuPrzejazduByTravelAndCategory(travelId,1,"PLN");
        float cenySamolotow=(float)mDb.przejazdDao().getSumKosztuPrzejazduByTravelAndCategory(travelId,2,"PLN");
        float cenyPociagu=(float)mDb.przejazdDao().getSumKosztuPrzejazduByTravelAndCategory(travelId,3,"PLN");
        float cenyStatku=(float)mDb.przejazdDao().getSumKosztuPrzejazduByTravelAndCategory(travelId,4,"PLN");
        float cenyRoweru=(float)mDb.przejazdDao().getSumKosztuPrzejazduByTravelAndCategory(travelId,5,"PLN");
        float cenyPieszo=(float)mDb.przejazdDao().getSumKosztuPrzejazduByTravelAndCategory(travelId,6,"PLN");
        float cenyInne=(float)mDb.przejazdDao().getSumKosztuPrzejazduByTravelAndCategory(travelId,7,"PLN");

        if(cenyAut>0) values.add(new PieEntry(cenyAut,getResources().getString(R.string.carlabel)));
        if(cenySamolotow>0) values.add(new PieEntry(cenySamolotow,getResources().getString(R.string.planelabel)));
        if(cenyPociagu>0) values.add(new PieEntry(cenyPociagu,getResources().getString(R.string.trainlabel)));
        if(cenyStatku>0) values.add(new PieEntry(cenyStatku,getResources().getString(R.string.shiplabel)));
        if(cenyRoweru>0) values.add(new PieEntry(cenyRoweru,getResources().getString(R.string.bikelabel)));
        if(cenyPieszo>0) values.add(new PieEntry(cenyPieszo,getResources().getString(R.string.onfootlabel)));
        if(cenyInne>0) values.add(new PieEntry(cenyInne,getResources().getString(R.string.otherlabel)));

        return values;


    }

    private float getBudgetRatio(){

        double budzetPodrozy=mDb.podrozDao().getPodrozById(travelId).getBudzet();
        double sumaZakupow=mDb.elementListyDoSpakowaniaDao().getSumOfShoppingList(packListId, true, true,"PLN");
        double sumaPrzejazdow=mDb.przejazdDao().getSumOfPrzejazdyByTravelId(travelId,"PLN");
        double sumaWydatkow=mDb.wydatekDao().getSumOfWydatkiByTravelId(travelId,"PLN");

        double sumaWszystkichWydatkow=sumaWydatkow+sumaPrzejazdow+sumaZakupow;

        float procentWydany=0;

        if(budzetPodrozy>0)  procentWydany=(float) (sumaWszystkichWydatkow/budzetPodrozy)*100;
        return Math.round(procentWydany);
    }

    public static int getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+1;
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
