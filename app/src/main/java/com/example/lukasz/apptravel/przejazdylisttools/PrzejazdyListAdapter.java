package com.example.lukasz.apptravel.przejazdylisttools;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import com.example.lukasz.apptravel.db.entities.Przejazd;

import java.util.ArrayList;
import java.util.List;

public class PrzejazdyListAdapter extends ArrayAdapter {

    private Context context;
    private int mResource;
    private ArrayList<Przejazd> lista;

    public PrzejazdyListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Przejazd> objects) {
        super(context, resource, objects);
        this.context=context;
        this.lista=objects;
        mResource=resource;
    }

    


    public void updateReceiptsList(List<Przejazd> newlist) {
        lista.clear();
        lista.addAll(newlist);
        notifyDataSetChanged();
    }
}
