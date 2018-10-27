package com.example.lukasz.apptravel.fragments;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.ElementListyDoSpakowania;
import com.example.lukasz.apptravel.packlisttools.PackListAdapter;

import java.util.ArrayList;
import java.util.List;

public class PackListClothesFragment extends Fragment {

    private long packListId;
    private ListView listView;
    private AppDatabase mDb;
    private PackListAdapter packListAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.packlistclothesfragmentlayout,container,false);
        packListId=getArguments().getLong("bundlePackListId");
        mDb= AppDatabase.getInstance(getContext());
        long categoryId=mDb.kategoriaDao().getIdKategoriiOdNazwy(getString(R.string.tabclotheslabel));
        ArrayList<ElementListyDoSpakowania> list=new ArrayList<ElementListyDoSpakowania>(mDb.elementListyDoSpakowaniaDao().
                getElementyListyDoSpakowaniaByKategoriaFromList(packListId,categoryId));

        listView=view.findViewById(R.id.listviewfragmentclothestopack);


        packListAdapter = new PackListAdapter(this.getContext(),R.layout.topacklistitemlayout,list);


        listView.setAdapter(packListAdapter);



        return view;
    }




}
