package com.example.lukasz.apptravel.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.statsTools.WykresProcentowyBudzetu;
import com.github.mikephil.charting.charts.HorizontalBarChart;

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
        travelId=intent.getLongExtra("travelId",0);
        mDb=AppDatabase.getInstance(this);

        wykresProcentBudzetu = findViewById(R.id.wykresprocentbudzetu);

        WykresProcentowyBudzetu wykresProcentowyBudzetu=new WykresProcentowyBudzetu(this, travelId);
        wykresProcentBudzetu=wykresProcentowyBudzetu.getWykresProcentowyBudzetu(wykresProcentBudzetu);
        wykresProcentBudzetu.animateXY(1500,1500);
        wykresProcentBudzetu.invalidate();

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
