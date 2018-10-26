package com.example.lukasz.apptravel.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.ElementListyDoSpakowania;
import com.example.lukasz.apptravel.packlisttools.PackListAdapter;

import java.util.ArrayList;

public class PackListOthersFragment extends Fragment {

    private long packListId;
    private ListView listView;
    private AppDatabase mDb;
    private PackListAdapter packListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.packlistothersfragmentlayout,container,false);
        packListId=getArguments().getLong("bundlePackListId");
        mDb= AppDatabase.getInstance(getContext());
        long categoryId=mDb.kategoriaDao().getIdKategoriiOdNazwy(getString(R.string.tabotherslabel));
        ArrayList<ElementListyDoSpakowania> list=new ArrayList<ElementListyDoSpakowania>(mDb.elementListyDoSpakowaniaDao().
                getElementyListyDoSpakowaniaByKategoriaFromList(packListId,categoryId));



        listView=view.findViewById(R.id.listviewfragmentotherstopack);

        packListAdapter = new PackListAdapter(this.getContext(),R.layout.topacklistitemlayout,list);
        listView.setAdapter(packListAdapter);



        return view;
    }
}
