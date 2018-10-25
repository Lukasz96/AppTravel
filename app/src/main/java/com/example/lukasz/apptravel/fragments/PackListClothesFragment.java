package com.example.lukasz.apptravel.fragments;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.lukasz.apptravel.db.entities.ElementListyDoSpakowania;
import com.example.lukasz.apptravel.packlisttools.PackListAdapter;

import java.util.ArrayList;

public class PackListClothesFragment extends Fragment {

    private long packListId;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.packlistclothesfragmentlayout,container,false);
        packListId=getArguments().getLong("bundlePackListId");
        System.out.println(" PACK LIST ID -------- "+packListId);
        ArrayList<ElementListyDoSpakowania> list=new ArrayList<>();
        list.add(new ElementListyDoSpakowania(1,5,"nazwa",false,
                false,11,5,false,3));
        list.add(new ElementListyDoSpakowania(2,6,"nazwa",true,
                false,2,5,false,3));
        list.add(new ElementListyDoSpakowania(3,7,"nazwa",false,
                false,3,5,false,3));
        list.add(new ElementListyDoSpakowania(4,8,"nazwa",true,
                false,4,5,false,3));



        listView=view.findViewById(R.id.listviewfragmentclothestopack);

        PackListAdapter packListAdapter = new PackListAdapter(this.getContext(),R.layout.topacklistitemlayout,list);


        listView.setAdapter(packListAdapter);



        return view;
    }




}
