package com.example.lukasz.apptravel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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


        if(mDb.listaDoSpakowaniaDao().getListaDoSpakowaniaByTravelId(travelId)==null){
            listaDoSpakowaniaId=mDb.listaDoSpakowaniaDao().insertListeDoSpakowania(new ListaDoSpakowania(0,travelName,travelId));
        }
        else listaDoSpakowaniaId=mDb.listaDoSpakowaniaDao().getListaDoSpakowaniaByTravelId(travelId).getId();

        ArrayList<ElementListyDoSpakowania> doKupieniaList = new ArrayList<ElementListyDoSpakowania>
                (mDb.elementListyDoSpakowaniaDao().getElementyCzyPrzekazanoDoZakupu(listaDoSpakowaniaId,true));

        listView=findViewById(R.id.shoppingList);
        fab=findViewById(R.id.addshopItem);

        ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(ShoppingListActivity.this,R.layout.shoppinglistitem,doKupieniaList);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menushoppinglist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.sendshopviafacebook:
                if(mDb.elementListyDoSpakowaniaDao().getElementyZDanejListyCzyDoKupienia(listaDoSpakowaniaId,true).isEmpty()){
                    Toast.makeText(this, R.string.shoppinglistempty, Toast.LENGTH_LONG).show();
                }
                else {

                    String output = getResources().getString(R.string.shoppinglistinit);
                    output+="\n";
                    List<ElementListyDoSpakowania> elementListyDoSpakowania = mDb.elementListyDoSpakowaniaDao().getElementyZDanejListyCzyDoKupienia(listaDoSpakowaniaId,true);
                    for (ElementListyDoSpakowania elementListyDoSpakowania1 : elementListyDoSpakowania) {
                        if(elementListyDoSpakowania1.isCzyKupione()){
                            output+="["+getResources().getString(R.string.boughtlabel)+"] ";
                        }
                        output+=elementListyDoSpakowania1.getNazwa();
                        output+=", ";
                        output+=elementListyDoSpakowania1.getIloscDoZakupu();
                        output+=" "+getResources().getString(R.string.quantityshort);
                        if(elementListyDoSpakowania.indexOf(elementListyDoSpakowania1) != (elementListyDoSpakowania.size() -1)) output+="\n";

                    }
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, output);
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
                }
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
