package com.example.lukasz.apptravel.shoppinglisttools;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.activities.EditPackListItemActivity;
import com.example.lukasz.apptravel.activities.EditShoppingListItemActivity;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.ElementListyDoSpakowania;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListAdapter extends ArrayAdapter<ElementListyDoSpakowania> {

    private Context context;
    private int mResource;
    private ArrayList<ElementListyDoSpakowania> lista;
    private CheckBox checkboxItem;
    private TextView menuItem;
    private TextView circleCounter;
    private TextView price;
    private AppDatabase mDb=AppDatabase.getInstance(context);


    public ShoppingListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ElementListyDoSpakowania> objects) {
        super(context, resource, objects);
        this.context=context;
        this.lista=objects;
        mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        boolean czyDoZakupu = getItem(position).isCzyPrzekazanoDoZakupu();
        boolean czyKupione = getItem(position).isCzyKupione();
        double cena = getItem(position).getCena();
        String nazwa = getItem(position).getNazwa();
        int ilosc=getItem(position).getIlosc();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(mResource,parent,false);

        checkboxItem=convertView.findViewById(R.id.checkBoxBought);
        menuItem=convertView.findViewById(R.id.shoppingListItemtextViewOptions);
        circleCounter=convertView.findViewById(R.id.shoppinglistitemcirclecounter);
        price=convertView.findViewById(R.id.textViewPrice);

        checkboxItem.setTag(position);
        checkboxItem.setOnCheckedChangeListener(checkListener);
        menuItem.setTag(position);

        ElementListyDoSpakowania currentEleement =getItem(position);

        checkboxItem.setText(currentEleement.getNazwa());
        circleCounter.setText(String.valueOf(currentEleement.getIlosc()));
        price.setText(R.string.pricelabel);
        price.append(" ");
        if(Double.toString(currentEleement.getCena()).equals("0.0")){
            price.append("?");
        }
        else price.append(Double.toString(currentEleement.getCena()));



        if(mDb.elementListyDoSpakowaniaDao().getElementListyDoSpakowaniaById(currentEleement.getId()).isCzyKupione()){
            checkboxItem.setChecked(true);
            checkboxItem.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
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
                                Intent intent = new Intent(context, EditShoppingListItemActivity.class);
                                intent.putExtra("itemId", elementListyDoSpakowania.getId());
                                context.startActivity(intent);
                                break;
                            case R.id.deletepacklistitem:
                                mDb.elementListyDoSpakowaniaDao().
                                        setCzyDoZakupu(elementListyDoSpakowania.getId(),false);
                                updateReceiptsList(mDb.elementListyDoSpakowaniaDao().getAllElementyDoZakupu(elementListyDoSpakowania.getListaDoSpakowaniaId(),true));
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


            if(elementListyDoSpakowania.getCena()==0 &&isChecked){
                buttonView.setChecked(false);


                Toast.makeText(context,R.string.nopricetobouy, Toast.LENGTH_LONG).show();
            }
            else if(elementListyDoSpakowania.getCena()>0 && isChecked) {
                mDb.elementListyDoSpakowaniaDao().setCzyKupione(elementListyDoSpakowania.getId(), isChecked);
                buttonView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
            else {
                buttonView.setPaintFlags(buttonView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                mDb.elementListyDoSpakowaniaDao().setCzyKupione(elementListyDoSpakowania.getId(), isChecked);
            }
        }
    };

    public void updateReceiptsList(List<ElementListyDoSpakowania> newlist) {
        lista.clear();
        lista.addAll(newlist);
        notifyDataSetChanged();
    }
}
