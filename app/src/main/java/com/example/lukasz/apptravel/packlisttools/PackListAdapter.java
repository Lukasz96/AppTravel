package com.example.lukasz.apptravel.packlisttools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.ElementListyDoSpakowania;
import com.example.lukasz.apptravel.fragments.PackListClothesFragment;

import java.util.ArrayList;
import java.util.List;

public class PackListAdapter extends ArrayAdapter<ElementListyDoSpakowania> {

    private Context context;
    private int mResource;
  //  private TextView textView;
    private TextView circleCounter;
    private CheckBox checkBox;
    private AppDatabase mDb=AppDatabase.getInstance(context);

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


         ElementListyDoSpakowania elementListyDoSpakowania=new ElementListyDoSpakowania(id,listaDoSpakowaniaId,
                 nazwa,czySpakowane,czyPrzekazanoDoZakupu,ilosc,cena,czyKupione,idKategorii);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(mResource,parent,false);



    //    textView=convertView.findViewById(R.id.packListItemTextView);
    //    textView.setTag(position);
        checkBox=convertView.findViewById(R.id.checkBoxPacked);
        checkBox.setTag(position);
        checkBox.setOnCheckedChangeListener(checkListener);
        circleCounter=convertView.findViewById(R.id.topacklistitemcirclecounter);

        checkBox.setText("IdEl: "+elementListyDoSpakowania.getId());
        checkBox.append("listaId: "+elementListyDoSpakowania.getListaDoSpakowaniaId());
        circleCounter.setText(String.valueOf(elementListyDoSpakowania.getIlosc()));

        if(elementListyDoSpakowania.isCzySpakowane()){
            checkBox.setChecked(true);
            checkBox.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
          //  parent.getChildAt(position).setBackgroundColor(Color.rgb(157,153,152));
        }



        return convertView;

    }

    CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            int position = (int)buttonView.getTag();
            ElementListyDoSpakowania elementListyDoSpakowania=getItem(position);
            mDb.elementListyDoSpakowaniaDao().updateCzySpakowanyElementById(elementListyDoSpakowania.getId(), isChecked);
            elementListyDoSpakowania.setCzySpakowane(isChecked);
            if(isChecked)buttonView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            else buttonView.setPaintFlags(buttonView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);

            System.out.println("ID ---- "+elementListyDoSpakowania.getId()+" czy spakowane ---- "
                    + elementListyDoSpakowania.isCzySpakowane()+" is checked= "+isChecked);
        }
    };
}
