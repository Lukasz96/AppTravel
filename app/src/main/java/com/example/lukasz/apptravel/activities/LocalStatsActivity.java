package com.example.lukasz.apptravel.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.StatsTools.LinechartTools;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.ElementListyDoSpakowania;
import com.example.lukasz.apptravel.db.entities.ListaDoSpakowania;
import com.github.mikephil.charting.charts.LineChart;

import java.util.List;

public class LocalStatsActivity extends AppCompatActivity {

    private long travelId;
    private AppDatabase mDb;
    private LineChart lineChart;
    private long packListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_stats);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.statslabel);

        Intent intent=getIntent();
        travelId=intent.getLongExtra("travelId",0);
        mDb=AppDatabase.getInstance(this);

        lineChart = (LineChart) findViewById(R.id.chart);

        ListaDoSpakowania listaDoSpakowania=mDb.listaDoSpakowaniaDao().getListaDoSpakowaniaByTravelId(travelId);
        packListId=listaDoSpakowania.getId();

       List<ElementListyDoSpakowania> elementListyDoSpakowania=mDb.elementListyDoSpakowaniaDao().getElementyZDanejListyCzyDoKupienia(packListId, true);

        LinechartTools linechartTools= new LinechartTools();
        lineChart.setData(linechartTools.getLineData(elementListyDoSpakowania));
     //   lineChart.setExtraBottomOffset(-450);

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
