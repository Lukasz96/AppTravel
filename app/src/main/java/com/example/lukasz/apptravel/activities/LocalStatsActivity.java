package com.example.lukasz.apptravel.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    private String glownaWaluta;
    private TextView budgetChartLabel;
    private TextView addiotionalBudgetInfo;
    private LinearLayout dodatkoweWykresy;
    private TextView labelWydatki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_stats);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.statslabel);

        budgetChartLabel=findViewById(R.id.budgetChart);

        Intent intent=getIntent();
        mDb=AppDatabase.getInstance(this);
        travelId=intent.getLongExtra("travelId",0);
        if(mDb.listaDoSpakowaniaDao().getListaDoSpakowaniaByTravelId(travelId)!=null)
        packListId=mDb.listaDoSpakowaniaDao().getListaDoSpakowaniaByTravelId(travelId).getId();
        else packListId=0;
        glownaWaluta=mDb.podrozDao().getWalutaByTravelId(travelId);
        addiotionalBudgetInfo=findViewById(R.id.additionbudgetinfo);
        dodatkoweWykresy=findViewById(R.id.dodatkoweWykresy);
        labelWydatki=findViewById(R.id.labelwydatki);


        //////////// DANE LICZBOWE ///////////////////////////////////////////

        List<String>walutykupione=mDb.elementListyDoSpakowaniaDao().getWalutySpakowaneKupione(packListId,true,true);
        List<String>walutyprzejazdy=mDb.przejazdDao().getWalutyPrzejazdy(travelId);
        List<String>walutywydatki=mDb.wydatekDao().getWalutyWydatki(travelId);

        List<String> razemRozneWaluty=new ArrayList<>();
        razemRozneWaluty.addAll(walutykupione);
        for(String waluta:walutyprzejazdy){
            if(!razemRozneWaluty.contains(waluta)){
                razemRozneWaluty.add(waluta);
            }
        }
        for(String waluta:walutywydatki){
            if(!razemRozneWaluty.contains(waluta)){
                razemRozneWaluty.add(waluta);
            }
        }

        int iloscDni=getDifferenceDays(mDb.podrozDao().getDateOdByTravelId(travelId),mDb.podrozDao().getDateDoByTravelId(travelId));
        double sumaZakupowGlownaWaluta=mDb.elementListyDoSpakowaniaDao().getSumOfShoppingList(packListId, true,true,glownaWaluta);
        double sumaPrzejazdowGlownaWaluta=mDb.przejazdDao().getSumOfPrzejazdyByTravelId(travelId,glownaWaluta);
        double sumaWydatkowGlownaWaluta=mDb.wydatekDao().getSumOfWydatkiByTravelId(travelId,glownaWaluta);

        double sumaWszystkichWydatkowGlownaWaluta=sumaWydatkowGlownaWaluta+sumaPrzejazdowGlownaWaluta+sumaZakupowGlownaWaluta;
        double averageCostGlownaWaluta=sumaWszystkichWydatkowGlownaWaluta/iloscDni;
        String averageGlownaWaluta = String.format("%.2f", averageCostGlownaWaluta);

        daneLiczbowe=findViewById(R.id.numericaldane);

        daneLiczbowe.append(getResources().getString(R.string.traveldurationlabel)+" ");


        daneLiczbowe.append(String.valueOf(iloscDni)+" ");

        if (iloscDni>1) daneLiczbowe.append(getResources().getString(R.string.dayslabel));
        else daneLiczbowe.append(getResources().getString(R.string.daylabel));

        daneLiczbowe.append("\n\n");

        daneLiczbowe.append(getResources().getString(R.string.totalcostlabel));
        daneLiczbowe.append(" "+String.format("%.2f", sumaWszystkichWydatkowGlownaWaluta)+" "+glownaWaluta);

        for(String waluta:razemRozneWaluty){
            if(!waluta.equals(glownaWaluta)){
                double sumaZakupowInnaWaluta=mDb.elementListyDoSpakowaniaDao().getSumOfShoppingList(packListId, true,true,waluta);
                double sumaPrzejazdowInnaWaluta=mDb.przejazdDao().getSumOfPrzejazdyByTravelId(travelId,waluta);
                double sumaWydatkowInnaWaluta=mDb.wydatekDao().getSumOfWydatkiByTravelId(travelId,waluta);

                double sumaWszystkichWydatkowInnaWaluta=sumaWydatkowInnaWaluta+sumaPrzejazdowInnaWaluta+sumaZakupowInnaWaluta;

                String averageInnaWaluta = String.format("%.2f", sumaWszystkichWydatkowInnaWaluta);
                daneLiczbowe.append(" + ");
                daneLiczbowe.append(averageInnaWaluta);
                daneLiczbowe.append(" "+waluta);

            }
        }
        daneLiczbowe.append("\n\n");



        daneLiczbowe.append(getResources().getString(R.string.averagecostlabel));


        daneLiczbowe.append(averageGlownaWaluta);
        daneLiczbowe.append(" "+glownaWaluta);

        for(String waluta:razemRozneWaluty){
            if(!waluta.equals(glownaWaluta)){
                double sumaZakupowInnaWaluta=mDb.elementListyDoSpakowaniaDao().getSumOfShoppingList(packListId, true,true,waluta);
                double sumaPrzejazdowInnaWaluta=mDb.przejazdDao().getSumOfPrzejazdyByTravelId(travelId,waluta);
                double sumaWydatkowInnaWaluta=mDb.wydatekDao().getSumOfWydatkiByTravelId(travelId,waluta);

                double sumaWszystkichWydatkowInnaWaluta=sumaWydatkowInnaWaluta+sumaPrzejazdowInnaWaluta+sumaZakupowInnaWaluta;
                double averageCostInnaWaluta=sumaWszystkichWydatkowInnaWaluta/iloscDni;
                String averageInnaWaluta = String.format("%.2f", averageCostInnaWaluta);
                daneLiczbowe.append(" + ");
                daneLiczbowe.append(averageInnaWaluta);
                daneLiczbowe.append(" "+waluta);
                addiotionalBudgetInfo.append("+ "+String.format("%.2f", sumaWszystkichWydatkowInnaWaluta)+" "+waluta+"\n");
            }
        }




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

        budgetChartLabel.append(" "+glownaWaluta+")");

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

        //////////// ILE DANEGO RODZAJ TRANSPOTU ////////////////////////////////
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
        labelWydatki.append(getResources().getString(R.string.expensespaidin));
        labelWydatki.append(" "+glownaWaluta+" "+getResources().getString(R.string.bycategory));
        wykresWszystkieWydatki=findViewById(R.id.wykreswydatki);
        float wydatkiNaZakupy=(float)Math.round(mDb.elementListyDoSpakowaniaDao().getSumOfShoppingList(packListId,true,true,glownaWaluta));
        float wydatkiNaNocleg=(float)Math.round(mDb.wydatekDao().getSumOfWydatkiByTravelIdAndCategory(travelId,1,glownaWaluta));
        float wydatkiNaJedzenie=(float)Math.round(mDb.wydatekDao().getSumOfWydatkiByTravelIdAndCategory(travelId,2,glownaWaluta));
        float wydatkiNaZwiedzanie=(float)Math.round(mDb.wydatekDao().getSumOfWydatkiByTravelIdAndCategory(travelId,3,glownaWaluta));
        float wydatkiInne=(float)Math.round(mDb.wydatekDao().getSumOfWydatkiByTravelIdAndCategory(travelId,4,glownaWaluta));
        float wydatkiTransport=(float)Math.round(mDb.przejazdDao().getSumOfPrzejazdyByTravelId(travelId,glownaWaluta));

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

        for(String waluta:razemRozneWaluty) {
            if (!waluta.equals(glownaWaluta)) {

                TextView labelDodatkowyWykres= new TextView(this);
                labelDodatkowyWykres.setMaxLines(3);
                labelDodatkowyWykres.setTextColor(getResources().getColor(R.color.colorPrimary));
                labelDodatkowyWykres.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                LinearLayout.LayoutParams lp2=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp2.setMargins(0,dpToPx(0),0,dpToPx(15));
                labelDodatkowyWykres.setLayoutParams(lp2);
                labelDodatkowyWykres.append(getResources().getString(R.string.expensespaidin));
                labelDodatkowyWykres.append(" "+waluta+" "+getResources().getString(R.string.bycategory));
                dodatkoweWykresy.addView(labelDodatkowyWykres);

                BarChart wykresWszystkieWydatkiInnaWaluta= new BarChart(this);
                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(300));
                lp.setMargins(0,dpToPx(0),0,dpToPx(15));
                wykresWszystkieWydatkiInnaWaluta.setLayoutParams(lp);



                float wydatkiNaZakupyInnaWaluta=(float)Math.round(mDb.elementListyDoSpakowaniaDao().getSumOfShoppingList(packListId,true,true,waluta));
                float wydatkiNaNoclegInnaWaluta=(float)Math.round(mDb.wydatekDao().getSumOfWydatkiByTravelIdAndCategory(travelId,1,waluta));
                float wydatkiNaJedzenieInnaWaluta=(float)Math.round(mDb.wydatekDao().getSumOfWydatkiByTravelIdAndCategory(travelId,2,waluta));
                float wydatkiNaZwiedzanieInnaWaluta=(float)Math.round(mDb.wydatekDao().getSumOfWydatkiByTravelIdAndCategory(travelId,3,waluta));
                float wydatkiInneInnaWaluta=(float)Math.round(mDb.wydatekDao().getSumOfWydatkiByTravelIdAndCategory(travelId,4,waluta));
                float wydatkiTransportInnaWaluta=(float)Math.round(mDb.przejazdDao().getSumOfPrzejazdyByTravelId(travelId,waluta));

                ArrayList<BarEntry> entryArrayListInnaWaluta= new ArrayList<>();
                entryArrayListInnaWaluta.add(new BarEntry(0,wydatkiNaZakupyInnaWaluta));
                entryArrayListInnaWaluta.add(new BarEntry(1,wydatkiNaNoclegInnaWaluta));
                entryArrayListInnaWaluta.add(new BarEntry(2,wydatkiNaJedzenieInnaWaluta));
                entryArrayListInnaWaluta.add(new BarEntry(3,wydatkiNaZwiedzanieInnaWaluta));
                entryArrayListInnaWaluta.add(new BarEntry(4,wydatkiInneInnaWaluta));
                entryArrayListInnaWaluta.add(new BarEntry(5,wydatkiTransportInnaWaluta));

                BarDataSet set1InnaWaluta= new BarDataSet(entryArrayListInnaWaluta,"");
                set1InnaWaluta.setColors(new int[]{Color.parseColor("#ffcc66"), Color.parseColor("#ccccff")
                        , Color.parseColor("#66ff99"),Color.parseColor("#cccc00"), Color.parseColor("#e67300"),
                        Color.parseColor("#cc6699")});
                set1InnaWaluta.setDrawValues(true);
                set1InnaWaluta.setValueTextSize(16);
                BarData barDataInnaWaluta= new BarData(set1InnaWaluta);

                wykresWszystkieWydatkiInnaWaluta.getAxisRight().setEnabled(false);
                wykresWszystkieWydatkiInnaWaluta.getLegend().setCustom(Arrays.asList(zakupy,noclegi,jedzenie,zwiedzanie,transport,inne));
                wykresWszystkieWydatkiInnaWaluta.getLegend().setWordWrapEnabled(true);
                wykresWszystkieWydatkiInnaWaluta.getDescription().setEnabled(false);
                wykresWszystkieWydatkiInnaWaluta.getXAxis().setDrawLabels(false);
                wykresWszystkieWydatkiInnaWaluta.getLegend().setTextSize(19);
                wykresWszystkieWydatkiInnaWaluta.getAxisLeft().setTextSize(16);
                wykresWszystkieWydatkiInnaWaluta.animateXY(650,650);
                wykresWszystkieWydatkiInnaWaluta.setData(barDataInnaWaluta);

                dodatkoweWykresy.addView(wykresWszystkieWydatkiInnaWaluta);
            }
        }


    }

    private ArrayList<PieEntry> getTransportData(){

        ArrayList<PieEntry> values = new ArrayList<>();

        float cenyAut=(float)mDb.przejazdDao().getPrzejazdyByTravelAndCategory(travelId,1).size();
        float cenySamolotow=(float)mDb.przejazdDao().getPrzejazdyByTravelAndCategory(travelId,2).size();
        float cenyPociagu=(float)mDb.przejazdDao().getPrzejazdyByTravelAndCategory(travelId,3).size();
        float cenyStatku=(float)mDb.przejazdDao().getPrzejazdyByTravelAndCategory(travelId,4).size();
        float cenyRoweru=(float)mDb.przejazdDao().getPrzejazdyByTravelAndCategory(travelId,5).size();
        float cenyPieszo=(float)mDb.przejazdDao().getPrzejazdyByTravelAndCategory(travelId,6).size();
        float cenyInne=(float)mDb.przejazdDao().getPrzejazdyByTravelAndCategory(travelId,7).size();

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
        double sumaZakupow=mDb.elementListyDoSpakowaniaDao().getSumOfShoppingList(packListId, true, true,glownaWaluta);
        double sumaPrzejazdow=mDb.przejazdDao().getSumOfPrzejazdyByTravelId(travelId,glownaWaluta);
        double sumaWydatkow=mDb.wydatekDao().getSumOfWydatkiByTravelId(travelId,glownaWaluta);

        double sumaWszystkichWydatkow=sumaWydatkow+sumaPrzejazdow+sumaZakupow;

        float procentWydany=0;

        if(budzetPodrozy>0)  procentWydany=(float) (sumaWszystkichWydatkow/budzetPodrozy)*100;
        return Math.round(procentWydany);
    }

    public static int getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+1;
    }
    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
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
