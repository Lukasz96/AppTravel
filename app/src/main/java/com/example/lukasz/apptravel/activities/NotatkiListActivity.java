package com.example.lukasz.apptravel.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.Notatka;
import com.example.lukasz.apptravel.notatkilisttools.NotatkiListAdapter;

import java.util.ArrayList;

public class NotatkiListActivity extends AppCompatActivity {

    private long travelId;
    protected ArrayList<Notatka> notatkaList;
    protected RecyclerView recyclerView;
    private AppDatabase mDb;
    private FloatingActionButton fab;
    WypelnijListe wypelnijListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notatki_list);

        mDb=AppDatabase.getInstance(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.notesbuttonlabel);

        fab=findViewById(R.id.buttonAddNotatka);

        Intent intent=getIntent();
        travelId=intent.getLongExtra("travelId",0);

     //   notatkaList=new ArrayList<>();
     //   notatkaList=mDb.notatkaDao().getNotatkiByTravelId(travelId);

        recyclerView=findViewById(R.id.listanotatek);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);

        wypelnijListe= new WypelnijListe();
        wypelnijListe.execute();
     //   NotatkiListAdapter notatkiListAdapter= new NotatkiListAdapter(this,notatkaList);
     //   recyclerView.setAdapter(notatkiListAdapter);



        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(NotatkiListActivity.this, AddNotatkaActivity.class);
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
        Intent intent=new Intent(NotatkiListActivity.this, TravelMainMenuActivity.class);
        intent.putExtra("travelId", travelId);
        startActivity(intent);
        finish();
    }

    public class WypelnijListe extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            notatkaList=new ArrayList<>(mDb.notatkaDao().getNotatkiByTravelId(travelId));
            NotatkiListAdapter notatkiListAdapter= new NotatkiListAdapter(NotatkiListActivity.this,notatkaList);
            recyclerView.setAdapter(notatkiListAdapter);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }
}
