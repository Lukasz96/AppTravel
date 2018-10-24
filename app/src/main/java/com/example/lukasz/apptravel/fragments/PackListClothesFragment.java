package com.example.lukasz.apptravel.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lukasz.apptravel.R;

public class PackListClothesFragment extends Fragment {

    private long packListId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.packlistclothesfragmentlayout,container,false);
        packListId=getArguments().getLong("bundlePackListId");

        String[] packlists={Long.toString(packListId)};

        ListView listView=view.findViewById(R.id.listviewfragmentclothestopack);

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                packlists
        );

        listView.setAdapter(arrayAdapter);

        return view;
    }
}
