package com.example.lukasz.apptravel.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.Podroz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GlobalStatsActivity extends AppCompatActivity {

    private TextView numberTravelsTv;
    private TextView numberdaysTv;
    private TextView numberridesTv;
    private TextView numbercurrenciesTv;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_stats);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.statsgloballabel);

        mDb=AppDatabase.getInstance(this);

        numberTravelsTv=findViewById(R.id.travelscoveredlabel);
        numberdaysTv=findViewById(R.id.travelsdays);
        numberridesTv=findViewById(R.id.travelsrideslabel);
        numbercurrenciesTv=findViewById(R.id.travelscurrencies);

        int howMuchTravels=mDb.podrozDao().getPodroze().size();
        int howMuchRides=mDb.przejazdDao().getAllPrzejazdy().size();

        int howMuchDays=0;
        for(Podroz podroz:mDb.podrozDao().getPodroze()){
            int ileDni=getDifferenceDays(podroz.getDataOd(),podroz.getDataDo());
            howMuchDays+=ileDni;
        }

        List<String> waluty=new ArrayList<>();
        for(Podroz podroz:mDb.podrozDao().getPodroze()){
            long travelId=podroz.getId();
            long listaId=0;
            if(mDb.listaDoSpakowaniaDao().getListaDoSpakowaniaByTravelId(travelId)!=null) listaId=mDb.listaDoSpakowaniaDao().getListaDoSpakowaniaByTravelId(travelId).getId();
            List<String>walutyShopping=mDb.elementListyDoSpakowaniaDao().getWalutySpakowaneKupione(listaId,true,true);
            List<String>walutyPrzejazdy=mDb.przejazdDao().getWalutyPrzejazdy(travelId);
            List<String>walutyWydatki=mDb.wydatekDao().getWalutyWydatki(travelId);

            for(String waluta:walutyShopping){
                if(!waluty.contains(waluta)) waluty.add(waluta);
            }
            for(String waluta:walutyPrzejazdy){
                if(!waluty.contains(waluta)) waluty.add(waluta);
            }
            for(String waluta:walutyWydatki){
                if(!waluty.contains(waluta)) waluty.add(waluta);
            }
        }

        int howMuchCurrencies=waluty.size();

        numberTravelsTv.append(howMuchTravels+" "+getResources().getString(R.string.daneIlePodrozy));
        numberdaysTv.append(howMuchDays+" "+getResources().getString(R.string.daneiledni));
        numbercurrenciesTv.append(howMuchCurrencies+" "+getResources().getString(R.string.daneilewalut));
        numberridesTv.append(howMuchRides+" "+getResources().getString(R.string.daneileprzejazdów));

        Spannable span = new SpannableString(numberTravelsTv.getText());
        span.setSpan(new RelativeSizeSpan(2f), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        numberTravelsTv.setText(span);

        Spannable span2 = new SpannableString(numberridesTv.getText());
        span2.setSpan(new RelativeSizeSpan(2f), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        numberridesTv.setText(span2);

        Spannable span3 = new SpannableString(numbercurrenciesTv.getText());
        span3.setSpan(new RelativeSizeSpan(2f), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        numbercurrenciesTv.setText(span3);

        Spannable span4 = new SpannableString(numberdaysTv.getText());
        span4.setSpan(new RelativeSizeSpan(2f), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        numberdaysTv.setText(span4);

    }

    public static int getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+1;
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
