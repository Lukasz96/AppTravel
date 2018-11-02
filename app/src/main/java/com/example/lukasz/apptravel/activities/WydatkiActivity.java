package com.example.lukasz.apptravel.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.Przejazd;
import com.example.lukasz.apptravel.db.entities.Wydatek;
import com.example.lukasz.apptravel.przejazdylisttools.PrzejazdyListAdapter;
import com.example.lukasz.apptravel.wydatkilisttools.WydatkiListAdapter;

import java.util.ArrayList;

public class WydatkiActivity extends AppCompatActivity {

    private long travelId;
    private AppDatabase mDb;
    private FloatingActionButton fab;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wydatki);

        Intent intent=getIntent();
        travelId=intent.getLongExtra("travelId",0);
        mDb=AppDatabase.getInstance(getApplicationContext());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.expensesbuttonlabel);

        fab=findViewById(R.id.buttonAddWydatek);
        listView=findViewById(R.id.wydatkilist);

        ArrayList<Wydatek> wydatkiList = new ArrayList<Wydatek>
                (mDb.wydatekDao().getWydatkiDlaPodrozy(travelId));

        WydatkiListAdapter wydatkiListAdapter = new WydatkiListAdapter(WydatkiActivity.this,R.layout.wydatekeatingitem,wydatkiList);
        listView.setAdapter(wydatkiListAdapter);


        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(WydatkiActivity.this, AddWydatekActivity.class);
                intent.putExtra("travelId", travelId);
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

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(WydatkiActivity.this, TravelMainMenuActivity.class);
        intent.putExtra("travelId", travelId);
        startActivity(intent);
        finish();
    }
}
