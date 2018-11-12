package com.example.lukasz.apptravel.wydatkilisttools;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.activities.EditPrzejazdItemActivity;
import com.example.lukasz.apptravel.activities.EditWydatekActivity;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.Przejazd;
import com.example.lukasz.apptravel.db.entities.Wydatek;

import java.util.ArrayList;
import java.util.List;

public class WydatkiListAdapter extends ArrayAdapter<Wydatek> {

    private Context context;
    private int mResource;
    private ArrayList<Wydatek> lista;
    private AppDatabase mDb=AppDatabase.getInstance(context);
    private ImageView ikonaWydatku;
    private TextView cenaWydatku;
    private TextView menuWydatku;
    private TextView nazwaWydatku;

    public WydatkiListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Wydatek> objects) {
        super(context, resource, objects);
        this.context=context;
        this.lista=objects;
        mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView= LayoutInflater.from(context).inflate(R.layout.wydatekeatingitem,parent,false);

        ikonaWydatku=convertView.findViewById(R.id.ikonawydatek);
        cenaWydatku=convertView.findViewById(R.id.textviewwydatekprice);
        nazwaWydatku=convertView.findViewById(R.id.textviewwydatekname);
        menuWydatku=convertView.findViewById(R.id.wydatekitemmenu);
        menuWydatku.setTag(position);

        switch ((int)getItem(position).getKategoriaWydatkuId()){
            case 1:
                ikonaWydatku.setImageResource(R.drawable.accomodationicon);
                break;
            case 2:
                ikonaWydatku.setImageResource(R.drawable.eatingicon);
                break;
            case 3:
                ikonaWydatku.setImageResource(R.drawable.sighseeingicon);
                break;
            case 4:
                ikonaWydatku.setImageResource(R.drawable.otherexpenseicon);
                break;
            default:
                ikonaWydatku.setImageResource(R.drawable.otherexpenseicon);
                break;
        }

        cenaWydatku.append(String.format("%.2f",getItem(position).getKoszt()));
        cenaWydatku.append(" ");
        cenaWydatku.append(getItem(position).getWaluta());
        nazwaWydatku.setText(getItem(position).getNazwa());

        menuWydatku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();

                Wydatek wydatek=getItem(position);
                long travelId=wydatek.getPodrozId();

                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.inflate(R.menu.packlistitemmenu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.editpacklistitem:
                                Intent intent = new Intent(context, EditWydatekActivity.class);
                                intent.putExtra("wydatekId", wydatek.getId());
                                intent.putExtra("travelId",travelId);
                                context.startActivity(intent);
                                break;
                            case R.id.deletepacklistitem:
                                mDb.wydatekDao().deleteWydatekById(wydatek.getId());
                                updateReceiptsList(mDb.wydatekDao().getWydatkiDlaPodrozy(travelId));
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

    public void updateReceiptsList(List<Wydatek> newlist) {
        lista.clear();
        lista.addAll(newlist);
        notifyDataSetChanged();
    }
}
