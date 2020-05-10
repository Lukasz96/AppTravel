package com.my.lukasz.apptravel.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.my.lukasz.apptravel.R;
import com.my.lukasz.apptravel.db.AppDatabase;
import com.my.lukasz.apptravel.db.entities.ElementListyDoSpakowania;
import com.my.lukasz.apptravel.db.entities.ListaDoSpakowania;
import com.my.lukasz.apptravel.db.entities.Podroz;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.my.lukasz.apptravel.db.entities.User;
import com.my.lukasz.apptravel.packlistgenerator.PodrozUzytkownik;
import com.my.lukasz.apptravel.packlistgenerator.RzeczDoSpakowania;
import com.my.lukasz.apptravel.packlistgenerator.collaborativeFiltering.CollaborativeFiltering;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NoExistingPacklistChioceActivity extends AppCompatActivity {

    private long travelId;
    private Button newListButton;
    private Button copyListButton;
    private Button collaborativeFilteringButton;
    private Button decisionTreeButton;
    private Button newMethodButton;
    private boolean areExistingPacklists;
    private Podroz podroz;
    private static final int kNN = 3;
    AppDatabase mDb;
    private AdView mAdView;

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
        collaborativeFilteringButton=findViewById(R.id.collaborativeFilteringButton);
        decisionTreeButton=findViewById(R.id.decisionTreeButton);
        newMethodButton=findViewById(R.id.newMethodButton);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.createingnewpacklistlabel);

        ConstraintLayout constraintLayout= findViewById(R.id.noexistingpacklist);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            constraintLayout.setBackgroundResource(R.drawable.main_menu_background);
            MobileAds.initialize(this, "ca-app-pub-9758633376103774~4671280518");
            mAdView = findViewById(R.id.adView4);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

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

        collaborativeFilteringButton.setOnClickListener(new View.OnClickListener() {
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
                long userId = mDb.podrozDao().getPodrozById(travelId).getUserId();
                Podroz podroz = mDb.podrozDao().getPodrozById(travelId);
                User user = mDb.userDao().getUserById(userId);
                int iloscDni = getDifferenceDays(podroz.getDataOd(), podroz.getDataDo());
                PodrozUzytkownik podrozUzytkownik =
                        new PodrozUzytkownik(iloscDni, (int)podroz.getKategoriaWakacjiId(), (int) podroz.getKategoriaTransportuId(),
                                (int) podroz.getKategoriaPogodyId(), mDb.plecDao().getNazwaPlciById(user.getPlecId()),
                                user.getWiek(), (int)travelId);
                CollaborativeFiltering collaborativeFiltering = new CollaborativeFiltering(kNN, podrozUzytkownik, getApplicationContext());
                List<RzeczDoSpakowania> rzeczDoSpakowania = new ArrayList<>();
                try {
                    rzeczDoSpakowania = collaborativeFiltering.getPackListRecommendation();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String waluta= mDb.podrozDao().getWalutaByTravelId(travelId);
                for (RzeczDoSpakowania rzeczDoSpakowaniaThing : rzeczDoSpakowania) {
                    long kategoria = getKategoriaRzeczyOdNazwy(rzeczDoSpakowaniaThing.getKategoria());
                    mDb.elementListyDoSpakowaniaDao().insertElementListyDoSpakowania(
                            new ElementListyDoSpakowania(0, packListId, rzeczDoSpakowaniaThing.getNazwa(), true,
                                    false, false, rzeczDoSpakowaniaThing.getIlosc(),
                                    rzeczDoSpakowaniaThing.getIlosc(), 0, waluta, false, kategoria)
                    );
                }

                intent.putExtra("packListId",packListId);
                finish();
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

    private static int getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+1;
    }

    private long getKategoriaRzeczyOdNazwy(String nazwa) {
        if (nazwa.equalsIgnoreCase("ubrania")) {
            return 1;
        }
        else if (nazwa.equalsIgnoreCase("higiena")) {
            return 2;
        }
        else if (nazwa.equalsIgnoreCase("dokumenty")) {
            return 3;
        }
        else if (nazwa.equalsIgnoreCase("elektronika")) {
            return 4;
        }
        else return 5;
    }
}
