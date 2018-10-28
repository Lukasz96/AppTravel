package com.example.lukasz.apptravel.packlisttools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.activities.EditPackListItemActivity;
import com.example.lukasz.apptravel.activities.PackListActivity;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.ElementListyDoSpakowania;
import com.example.lukasz.apptravel.fragments.PackListClothesFragment;
import com.example.lukasz.apptravel.fragments.PackListDocumentsFragment;
import com.example.lukasz.apptravel.fragments.PackListHygieneFragment;
import com.example.lukasz.apptravel.fragments.PackListOthersFragment;

import java.util.ArrayList;
import java.util.List;

public class PackListAdapter extends ArrayAdapter<ElementListyDoSpakowania> {

    private Context context;
    private int mResource;
    private ArrayList<ElementListyDoSpakowania> lista;
    private TextView circleCounter;
    private CheckBox checkBox;
    private TextView menuItem;
    private AppDatabase mDb=AppDatabase.getInstance(context);

    public PackListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ElementListyDoSpakowania> objects) {
        super(context, resource, objects);
        this.context=context;
        this.lista=objects;
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
         boolean czyDoSpakowania=getItem(position).isCzyDoSpakowania();
         boolean czyPrzekazanoDoZakupu=getItem(position).isCzyPrzekazanoDoZakupu();
         int ilosc=getItem(position).getIlosc();
         double cena=getItem(position).getCena();
         boolean czyKupione=getItem(position).isCzyKupione();
         long idKategorii=getItem(position).getIdKategorii();


         ElementListyDoSpakowania elementListyDoSpakowania=new ElementListyDoSpakowania(id,listaDoSpakowaniaId,
                 nazwa,czyDoSpakowania,czySpakowane,czyPrzekazanoDoZakupu,ilosc,cena,czyKupione,idKategorii);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(mResource,parent,false);



    //    textView=convertView.findViewById(R.id.packListItemTextView);
    //    textView.setTag(position);
        checkBox=convertView.findViewById(R.id.checkBoxPacked);
        checkBox.setTag(position);
        checkBox.setOnCheckedChangeListener(checkListener);
        circleCounter=convertView.findViewById(R.id.topacklistitemcirclecounter);
        menuItem=convertView.findViewById(R.id.packListItemtextViewOptions);
        menuItem.setTag(position);
        checkBox.setText(elementListyDoSpakowania.getNazwa());
        circleCounter.setText(String.valueOf(elementListyDoSpakowania.getIlosc()));

        if(elementListyDoSpakowania.isCzySpakowane()){
            checkBox.setChecked(true);
            checkBox.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
          //  parent.getChildAt(position).setBackgroundColor(Color.rgb(157,153,152));
        }

        menuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();
                ElementListyDoSpakowania elementListyDoSpakowania=getItem(position);

                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.inflate(R.menu.packlistitemmenu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.editpacklistitem:
                                Intent intent = new Intent(context, EditPackListItemActivity.class);
                                intent.putExtra("itemId", elementListyDoSpakowania.getId());
                                context.startActivity(intent);
                                break;
                            case R.id.deletepacklistitem:
                                mDb.elementListyDoSpakowaniaDao().
                                        setCzyDoSpakowania(elementListyDoSpakowania.getId(),false);
                                   //     deleteElementListyDoSpakowaniaById(elementListyDoSpakowania.getId());
                                        updateReceiptsList(mDb.elementListyDoSpakowaniaDao().
                                                getElementyListyDoSpakowaniaByKategoriaFromListDoSpakowania(listaDoSpakowaniaId,
                                                        elementListyDoSpakowania.getIdKategorii(),true));
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });



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
            //elementListyDoSpakowania.setCzySpakowane(isChecked);
            if(isChecked)buttonView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            else buttonView.setPaintFlags(buttonView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }
    };

    public void updateReceiptsList(List<ElementListyDoSpakowania> newlist) {
        lista.clear();
        lista.addAll(newlist);
        notifyDataSetChanged();
    }


}
