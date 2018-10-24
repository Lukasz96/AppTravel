package com.example.lukasz.apptravel.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lukasz.apptravel.R;

public class PackListDocumentsFragment extends Fragment {

    private long packListId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        packListId=getArguments().getLong("bundlePackListId");
        return inflater.inflate(R.layout.packlistdocumentsfragmentlayout,container,false);
    }
}
