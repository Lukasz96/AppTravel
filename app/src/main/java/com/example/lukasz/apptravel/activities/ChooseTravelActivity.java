package com.example.lukasz.apptravel.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.Podroz;
import com.example.lukasz.apptravel.podrozelisttools.PodrozeAdapter;

import java.util.List;

public class ChooseTravelActivity extends AppCompatActivity {

    private ListView listView;
    private List<Podroz> podrozList;
    private AppDatabase mDb;
    private PodrozeAdapter podrozeAdapter;
    private long travelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_travel);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.choosetravellabel);

        ConstraintLayout constraintLayout= findViewById(R.id.choosetravelactivity);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            constraintLayout.setBackgroundResource(R.drawable.main_menu_background);
        }
        else {
            constraintLayout.setBackgroundResource(R.drawable.main_menu_background_landscape);
        }

        mDb=AppDatabase.getInstance(this);
        listView=findViewById(R.id.listapodrozy);
        podrozList=mDb.podrozDao().getPodrozeOrderedByDate();
        podrozeAdapter= new PodrozeAdapter(this, R.layout.listofpacklistsitemlayout,podrozList);
        listView.setAdapter(podrozeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                travelId=podrozList.get(position).getId();
                Intent intent= new Intent(ChooseTravelActivity.this, TravelMainMenuActivity.class);
                intent.putExtra("travelId",travelId);
                startActivity(intent);
            }
        });

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
}
