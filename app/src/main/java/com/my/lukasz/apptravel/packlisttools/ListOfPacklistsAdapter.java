package com.my.lukasz.apptravel.packlisttools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.my.lukasz.apptravel.R;
import com.my.lukasz.apptravel.db.AppDatabase;
import com.my.lukasz.apptravel.db.entities.ListaDoSpakowania;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListOfPacklistsAdapter extends ArrayAdapter<ListaDoSpakowania> {


    private Context context;
    private AppDatabase mDb=AppDatabase.getInstance(context);
    private TextView nameOfPacklist;
    private TextView dateOfPacklist;
    private int mResource;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private LinearLayout linearLayout;

    public ListOfPacklistsAdapter(@NonNull Context context, int resource, @NonNull List<ListaDoSpakowania> objects) {
        super(context, resource, objects);
        this.context=context;
        this.mResource=resource;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){



        ListaDoSpakowania listaDoSpakowania = getItem(position);

        String nazwa=listaDoSpakowania.getNazwa();
        long travelId = listaDoSpakowania.getPodrozId();
        Date dataOd=mDb.podrozDao().getDateOdByTravelId(travelId);

        Date dataDo=mDb.podrozDao().getDateDoByTravelId(travelId);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(mResource,parent,false);

        nameOfPacklist=convertView.findViewById(R.id.packlistname);
        dateOfPacklist=convertView.findViewById(R.id.packlistdate);
        linearLayout=convertView.findViewById(R.id.listToPackLinear);
        linearLayout.setTag(position);

        nameOfPacklist.setText(nazwa);
        dateOfPacklist.setText(dateFormat.format(dataOd)+" - ");
        dateOfPacklist.append(dateFormat.format(dataDo));





        return convertView;
    }


}
