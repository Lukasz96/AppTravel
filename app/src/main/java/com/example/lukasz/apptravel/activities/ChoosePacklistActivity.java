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

import java.util.ArrayList;
import java.util.Iterator;
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
        List<ListaDoSpakowania> ostateczaList= new ArrayList<>();
        for(ListaDoSpakowania listaDoSpakowania:doSpakowaniaList){

            System.out.println("nazwa "+ listaDoSpakowania.getNazwa());
            System.out.println("id "+listaDoSpakowania.getId());
            if(mDb.elementListyDoSpakowaniaDao().getElementyZDanejListyCzyDoSpakowania(listaDoSpakowania.getId(),true).size()==0){
                System.out.println("NIE MA NIC DO SPAK");
            }
            else{
                ostateczaList.add(listaDoSpakowania);
            }
        }

        listView=findViewById(R.id.listOfListToPack);

        listOfPacklistsAdapter = new ListOfPacklistsAdapter(getApplicationContext(),R.layout.listofpacklistsitemlayout,ostateczaList);


        listView.setAdapter(listOfPacklistsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                podrozWybrana=mDb.podrozDao().getPodrozById(ostateczaList.get(position).getPodrozId());
                podrozObecna=mDb.podrozDao().getPodrozById(podrozIdObecna);

                ListaDoSpakowania listaDoSpakowaniaDoSkopiowania=mDb.listaDoSpakowaniaDao().getListaDoSpakowaniaByTravelId(podrozWybrana.getId());

                List<ElementListyDoSpakowania> elementListyDoSpakowaniaDoKopiowania=mDb.elementListyDoSpakowaniaDao().
                        getElementyZDanejListyCzyDoSpakowania(listaDoSpakowaniaDoSkopiowania.getId(),true);
                long newpackListId=0;
                int i=0;
                if(mDb.listaDoSpakowaniaDao().getListaDoSpakowaniaByTravelId(podrozIdObecna)==null && i<1) {
                    i++;
                    newpackListId = mDb.listaDoSpakowaniaDao().insertListeDoSpakowania(
                            new ListaDoSpakowania(0, podrozObecna.getNazwa(), podrozObecna.getId()));

                }
                else if(i<1) {
                    i++;
                    newpackListId=mDb.listaDoSpakowaniaDao().getListaDoSpakowaniaByTravelId(podrozIdObecna).getId();

                }

                for(ElementListyDoSpakowania element:elementListyDoSpakowaniaDoKopiowania){
                    String nazwa=element.getNazwa();
                    int iloscDoSpakowania=element.getIloscDoSpakowania();

                    long idKategorii=element.getIdKategorii();

                    mDb.elementListyDoSpakowaniaDao().insertElementListyDoSpakowania(
                            new ElementListyDoSpakowania(0, newpackListId, nazwa,true,false,false, iloscDoSpakowania,iloscDoSpakowania,
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
