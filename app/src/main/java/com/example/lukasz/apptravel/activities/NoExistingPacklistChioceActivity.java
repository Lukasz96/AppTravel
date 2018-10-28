package com.example.lukasz.apptravel.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.ListaDoSpakowania;
import com.example.lukasz.apptravel.db.entities.Podroz;
import com.example.lukasz.apptravel.imageCalc.BackgroundImageCalc;

public class NoExistingPacklistChioceActivity extends AppCompatActivity {

    private long travelId;
    private Button newListButton;
    private Button copyListButton;
    private boolean areExistingPacklists;
    private Podroz podroz;
    AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_existing_packlist_chioce);

        Intent intent=getIntent();
        travelId=intent.getLongExtra("travelId",0);
        mDb=AppDatabase.getInstance(getApplicationContext());
        areExistingPacklists=intent.getBooleanExtra("areExistingPacklists", false);
        podroz=mDb.podrozDao().getPodrozById(travelId);
        newListButton=findViewById(R.id.buttonnewpacklist);
        copyListButton=findViewById(R.id.buttoncopypacklist);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.createingnewpacklistlabel);

        ConstraintLayout constraintLayout= findViewById(R.id.noexistingpacklist);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            constraintLayout.setBackgroundResource(R.drawable.main_menu_background);
        }
        else {
            constraintLayout.setBackgroundResource(R.drawable.main_menu_background_landscape);
        }

        newListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(NoExistingPacklistChioceActivity.this, PackListActivity.class);
                intent.putExtra("travelId",travelId);
                long packListId;
                if(mDb.listaDoSpakowaniaDao().getListaDoSpakowaniaByTravelId(travelId)==null) {
                    packListId = mDb.listaDoSpakowaniaDao().insertListeDoSpakowania(
                            new ListaDoSpakowania(0, podroz.getNazwa(), podroz.getId()));
                }
                else {
                    packListId=mDb.listaDoSpakowaniaDao().getListaDoSpakowaniaByTravelId(travelId).getId();
                }
                intent.putExtra("packListId",packListId);
                finish();
                startActivity(intent);
            }
        });

        copyListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(areExistingPacklists==false){
                    Toast.makeText(NoExistingPacklistChioceActivity.this, getString(R.string.noexistinglistlabel), Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent=new Intent(NoExistingPacklistChioceActivity.this, ChoosePacklistActivity.class);
                    intent.putExtra("travelId",travelId);
                    startActivity(intent);
                }
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
