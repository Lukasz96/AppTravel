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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.ElementListyDoSpakowania;
import com.example.lukasz.apptravel.db.entities.ListaDoSpakowania;
import com.example.lukasz.apptravel.db.entities.Podroz;
import com.example.lukasz.apptravel.imageCalc.BackgroundImageCalc;
import com.example.lukasz.apptravel.packlisttools.ListOfPacklistsAdapter;
import com.example.lukasz.apptravel.packlisttools.PackListAdapter;

import java.util.List;

public class ChoosePacklistActivity extends AppCompatActivity {

    private long podrozIdObecna;
    private AppDatabase mDb;
    private ListView listView;
    private ListOfPacklistsAdapter listOfPacklistsAdapter;
    private Podroz podrozWybrana;
    private Podroz podrozObecna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_packlist);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.choosepacklisttocopylabel);

        Intent intent=getIntent();
        podrozIdObecna=intent.getLongExtra("travelId",0);
        mDb=AppDatabase.getInstance(getApplicationContext());

        List<ListaDoSpakowania> doSpakowaniaList=mDb.listaDoSpakowaniaDao().getAllListyDoSpakowaniaOrderedByDate();

        listView=findViewById(R.id.listOfListToPack);

        listOfPacklistsAdapter = new ListOfPacklistsAdapter(getApplicationContext(),R.layout.listofpacklistsitemlayout,doSpakowaniaList);


        listView.setAdapter(listOfPacklistsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
              //  Toast.makeText(getApplicationContext(),"CLICKED - "+position,Toast.LENGTH_LONG).show();
                podrozWybrana=mDb.podrozDao().getPodrozById(doSpakowaniaList.get(position).getPodrozId());
                podrozObecna=mDb.podrozDao().getPodrozById(podrozIdObecna);
          //      System.out.println("wybrana pozdroz ---------(ma byc 20) "+podrozWybrana.getId());
                ListaDoSpakowania listaDoSpakowaniaDoSkopiowania=mDb.listaDoSpakowaniaDao().getListaDoSpakowaniaByTravelId(podrozWybrana.getId());
           //     System.out.println("lista do skopiowania id (ma byc 11) - "+listaDoSpakowaniaDoSkopiowania.getId());
                List<ElementListyDoSpakowania> elementListyDoSpakowaniaDoKopiowania=mDb.elementListyDoSpakowaniaDao().
                        getElementyDoSpakowaniaZDanejListy(listaDoSpakowaniaDoSkopiowania.getId());
                long newpackListId=mDb.listaDoSpakowaniaDao().insertListeDoSpakowania(
                        new ListaDoSpakowania(0,podrozObecna.getNazwa(),podrozObecna.getId()));
           //     System.out.println("ILE SKOPPIOWAC ELEMENTOW ---------- "+elementListyDoSpakowaniaDoKopiowania.size());
                for(ElementListyDoSpakowania element:elementListyDoSpakowaniaDoKopiowania){
                    String nazwa=element.getNazwa();
                    int ilosc=element.getIlosc();
                    long idKategorii=element.getIdKategorii();

                    mDb.elementListyDoSpakowaniaDao().insertElementListyDoSpakowania(
                            new ElementListyDoSpakowania(0, newpackListId, nazwa,false,false, ilosc,
                                    0, false, idKategorii));
                }
                Intent intent = new Intent(ChoosePacklistActivity.this, PackListActivity.class);
                intent.putExtra("travelId", podrozIdObecna);
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


}
