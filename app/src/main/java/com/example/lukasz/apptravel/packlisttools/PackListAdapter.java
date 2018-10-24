package com.example.lukasz.apptravel.packlisttools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.entities.ElementListyDoSpakowania;

import java.util.ArrayList;
import java.util.List;

public class PackListAdapter extends ArrayAdapter<ElementListyDoSpakowania> {

    private Context context;
    private int mResource;

    public PackListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ElementListyDoSpakowania> objects) {
        super(context, resource, objects);
        this.context=context;
        mResource=resource;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

         long id=getItem(position).getId();
         long listaDoSpakowaniaId=getItem(position).getListaDoSpakowaniaId();
         String nazwa=getItem(position).getNazwa();
         boolean czySpakowane=getItem(position).isCzySpakowane();
         boolean czyPrzekazanoDoZakupu=getItem(position).isCzyPrzekazanoDoZakupu();
         int ilosc=getItem(position).getIlosc();
         double cena=getItem(position).getCena();
         boolean czyKupione=getItem(position).isCzyKupione();
         long idKategorii=getItem(position).getIdKategorii();
         String uwaga=getItem(position).getUwaga();

         ElementListyDoSpakowania elementListyDoSpakowania=new ElementListyDoSpakowania(id,listaDoSpakowaniaId,
                 nazwa,czySpakowane,czyPrzekazanoDoZakupu,ilosc,cena,czyKupione,idKategorii,uwaga);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(mResource,parent,false);

        TextView textView=convertView.findViewById(R.id.packListItemTextView);
        CheckBox checkBox=convertView.findViewById(R.id.checkBoxPacked);

        textView.setText("IdEl: "+id);
        textView.append("listaId: "+listaDoSpakowaniaId);

        if(elementListyDoSpakowania.isCzySpakowane()){
            checkBox.setChecked(true);
            textView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
          //  parent.getChildAt(position).setBackgroundColor(Color.rgb(157,153,152));
        }
        return convertView;

    }
}
