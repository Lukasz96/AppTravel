package com.example.lukasz.apptravel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.ElementListyDoSpakowania;
import com.example.lukasz.apptravel.db.entities.ListaDoSpakowania;
import com.example.lukasz.apptravel.shoppinglisttools.ShoppingListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    private long travelId;
    private AppDatabase mDb;
    private long listaDoSpakowaniaId;
    private ListView listView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.shoppinglistlabel);

        Intent intent=getIntent();
        travelId=intent.getLongExtra("travelId",0);
        mDb=AppDatabase.getInstance(getApplicationContext());
        String travelName=mDb.podrozDao().getPodrozById(travelId).getNazwa();
        long listaDoSpakowaniaId;

        if(mDb.listaDoSpakowaniaDao().getListaDoSpakowaniaByTravelId(travelId)==null){
            listaDoSpakowaniaId=mDb.listaDoSpakowaniaDao().insertListeDoSpakowania(new ListaDoSpakowania(0,travelName,travelId));
        }
        else listaDoSpakowaniaId=mDb.listaDoSpakowaniaDao().getListaDoSpakowaniaByTravelId(travelId).getId();

        ArrayList<ElementListyDoSpakowania> doKupieniaList = new ArrayList<ElementListyDoSpakowania>
                (mDb.elementListyDoSpakowaniaDao().getElementyCzyPrzekazanoDoZakupu(listaDoSpakowaniaId,true));

        listView=findViewById(R.id.shoppingList);
        fab=findViewById(R.id.addshopItem);

        ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(getApplicationContext(),R.layout.shoppinglistitem,doKupieniaList);
        listView.setAdapter(shoppingListAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingListActivity.this, AddNewShopListItemActivity.class);
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
        Intent intent=new Intent(ShoppingListActivity.this, TravelMainMenuActivity.class);
        intent.putExtra("travelId", travelId);
        startActivity(intent);
        finish();
    }

}
