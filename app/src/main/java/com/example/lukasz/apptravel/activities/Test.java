package com.example.lukasz.apptravel.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.Kategoria;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        TextView textView=(TextView)findViewById(R.id.textView2);
        AppDatabase mDb= AppDatabase.getInstance(this.getApplicationContext());
        List<Kategoria> kategoriaList=mDb.kategoriaDao().getAllKategoria();
        for (Kategoria kategoria:kategoriaList){
            textView.append(kategoria.getNazwaKategorii());
            System.out.println(kategoria.getNazwaKategorii());
        }
        Date date=mDb.podrozDao().getDate();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        textView.append(date.toString());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // perform your action here
    }
}
