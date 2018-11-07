package com.example.lukasz.apptravel.statsTools;

import android.content.Context;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class WykresProcentowyBudzetu {

    private Context context;
    private long travelId;
    private AppDatabase mDb;
    private long packListId;

    public WykresProcentowyBudzetu(Context context, long travelId){
        this.context=context;
        this.travelId=travelId;
        mDb=AppDatabase.getInstance(context);
    }

    public HorizontalBarChart getWykresProcentowyBudzetu(HorizontalBarChart horizontalBarChart){

        YAxis left = horizontalBarChart.getAxisLeft();
        left.setDrawLabels(false); // no axis labels
        double budzetPodrozy=mDb.podrozDao().getPodrozById(travelId).getBudzet();
        packListId=mDb.listaDoSpakowaniaDao().getListaDoSpakowaniaByTravelId(travelId).getId();
        double sumaZakupow=mDb.elementListyDoSpakowaniaDao().getSumOfShoppingList(packListId, true);
        double sumaPrzejazdow=mDb.przejazdDao().getSumOfPrzejazdyByTravelId(travelId);
        double sumaWydatkow=mDb.wydatekDao().getSumOfWydatkiByTravelId(travelId);

        double sumaWszystkichWydatkow=sumaWydatkow+sumaPrzejazdow+sumaZakupow;

        float procentWydany=0;

        if(budzetPodrozy>0)  procentWydany=(float) (sumaWszystkichWydatkow/budzetPodrozy)*100;
        List<BarEntry> yVals=new ArrayList<>();
        BarEntry entry=new BarEntry(1,procentWydany);

        yVals.add(entry);
        BarDataSet set= new BarDataSet(yVals,"");
        set.setColor(R.color.white);

        BarData data=new BarData(set);
        data.setBarWidth(0.33f);
        horizontalBarChart.setData(data);
        horizontalBarChart.getAxisLeft().setEnabled(false);
        horizontalBarChart.getXAxis().setEnabled(false);
        horizontalBarChart.getDescription().setEnabled(false);
        horizontalBarChart.getAxisRight().setTextSize(17);
        horizontalBarChart.getAxisRight().setAxisMinimum(0);
        horizontalBarChart.getAxisRight().setAxisMaximum(150);
        //horizontalBarChart.getLegend().setEnabled(false);

        LimitLine limitLine=new LimitLine(100f,"100%");
        limitLine.setLineWidth(2f);
        limitLine.setTextSize(14);
        limitLine.setTextColor(R.color.red);
        limitLine.setLineColor(R.color.red);
        horizontalBarChart.getAxisRight().addLimitLine(limitLine);


        return horizontalBarChart;

    }
}
