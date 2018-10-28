package com.example.lukasz.apptravel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.ElementListyDoSpakowania;

import java.util.List;

public class ShoppingListActivity extends Activity {

    private long travelId;
    private AppDatabase mDb;
    private long listaDoSpakowaniaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        Intent intent=getIntent();
        travelId=intent.getLongExtra("travelId",0);
        mDb=AppDatabase.getInstance(getApplicationContext());

        listaDoSpakowaniaId=mDb.listaDoSpakowaniaDao().deleteListaDoSpakowaniaByTravelId(travelId);

        List<ElementListyDoSpakowania> doSpakowaniaList = mDb.elementListyDoSpakowaniaDao().getElementyCzyPrzekazanoDoZakupu(listaDoSpakowaniaId,true);
        
    }

}
