package com.my.lukasz.apptravel.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.my.lukasz.apptravel.R;
import com.my.lukasz.apptravel.db.AppDatabase;
import com.my.lukasz.apptravel.db.entities.Przejazd;
import com.my.lukasz.apptravel.przejazdylisttools.PrzejazdyListAdapter;

import java.util.ArrayList;

public class PrzejazdyActivity extends AppCompatActivity {

    private long travelId;
    private FloatingActionButton fab;
    private AppDatabase mDb;
    private ListView listView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przejazdy);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.ridesactivitylabel);

        fab=findViewById(R.id.buttonAddPrzejazd);

        Intent intent=getIntent();
        travelId=intent.getLongExtra("travelId",0);
        mDb=AppDatabase.getInstance(getApplicationContext());

        ArrayList<Przejazd> przejazdyList = new ArrayList<Przejazd>
                (mDb.przejazdDao().getPrzejazdyDlaPodrozy(travelId));

        listView=findViewById(R.id.przejazdyList);


        PrzejazdyListAdapter przejazdyListAdapter = new PrzejazdyListAdapter(PrzejazdyActivity.this,R.layout.przejazdmiddleitem,przejazdyList);
        listView.setAdapter(przejazdyListAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PrzejazdyActivity.this, AddNewPrzejazdActivity.class);
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
        Intent intent=new Intent(PrzejazdyActivity.this, TravelMainMenuActivity.class);
        intent.putExtra("travelId", travelId);
        startActivity(intent);
        finish();
    }
}
