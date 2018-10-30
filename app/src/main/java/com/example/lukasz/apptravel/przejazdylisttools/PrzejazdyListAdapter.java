package com.example.lukasz.apptravel.przejazdylisttools;

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
import com.example.lukasz.apptravel.activities.EditShoppingListItemActivity;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.ElementListyDoSpakowania;
import com.example.lukasz.apptravel.db.entities.Przejazd;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrzejazdyListAdapter extends ArrayAdapter<Przejazd> {

    private static final int ONLYONEITEM=0;
    private static final int FIRSTITEM=1;
    private static final int MIDDLEITEM=2;
    private static final int LASTITEM=3;

    private Context context;
    private int mResource;
    private ArrayList<Przejazd> lista;
    private ImageView ikonaPrzejazdu;
    private TextView nazwaPrzejazdu;
    private TextView dataPrzejazdu;
    private TextView cenaPrzejazdu;
    private TextView menuPrzejazdu;
    private AppDatabase mDb=AppDatabase.getInstance(context);

    public PrzejazdyListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Przejazd> objects) {
        super(context, resource, objects);
        this.context=context;
        this.lista=objects;
        mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null){
            if(getItemViewType(position)==ONLYONEITEM){
                convertView=LayoutInflater.from(context).inflate(R.layout.przejazdonlyoneitem,parent,false);
            }
            else if (getItemViewType(position)==FIRSTITEM){
                convertView=LayoutInflater.from(context).inflate(R.layout.przejazdfirstitem,parent,false);
            }
            else if (getItemViewType(position)==LASTITEM){
                convertView=LayoutInflater.from(context).inflate(R.layout.przejazdlastitem,parent,false);
            }
            else {
                convertView=LayoutInflater.from(context).inflate(R.layout.przejazdmiddleitem,parent,false);
            }
        }



        ikonaPrzejazdu=convertView.findViewById(R.id.ikonatransport);
        nazwaPrzejazdu=convertView.findViewById(R.id.textviewridename);
        dataPrzejazdu=convertView.findViewById(R.id.textviewridedate);
        cenaPrzejazdu=convertView.findViewById(R.id.textviewrideprice);
        menuPrzejazdu=convertView.findViewById(R.id.textView6);
        menuPrzejazdu.setTag(position);

        Przejazd przejazd = getItem(position);

        Date dataOd=przejazd.getDataOd();
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy , HH:mm:ss");
        String dateFormated=dt.format(dataOd);
        String cutSeconds=dateFormated.substring(0,18);

        nazwaPrzejazdu.setText(przejazd.getNazwa());

        dataPrzejazdu.setText(cutSeconds);


        if(przejazd.getKoszt()==0){
            cenaPrzejazdu.setText(R.string.pricelabel);
            cenaPrzejazdu.append(" ?");
        }
        else {
            cenaPrzejazdu.setText(R.string.pricelabel);
            cenaPrzejazdu.append(" ");
            cenaPrzejazdu.append(przejazd.getKoszt().toString());
        }

        switch ((int)przejazd.getKategoriaPrzejazduId()){
            case 1:
                ikonaPrzejazdu.setImageResource(R.drawable.caricon);
                break;
            case 2:
                ikonaPrzejazdu.setImageResource(R.drawable.planeicon);
                break;
            case 3:
                ikonaPrzejazdu.setImageResource(R.drawable.trainicon);
                break;
            case 4:
                ikonaPrzejazdu.setImageResource(R.drawable.shipicon);
                break;
            case 5:
                ikonaPrzejazdu.setImageResource(R.drawable.bikeicon);
                break;
            case 6:
                ikonaPrzejazdu.setImageResource(R.drawable.byfooticon);
                break;
            case 7:
                ikonaPrzejazdu.setImageResource(R.drawable.unknownrideicon);
                break;
            default:
                ikonaPrzejazdu.setImageResource(R.drawable.unknownrideicon);
                break;
        }

        menuPrzejazdu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();

                Przejazd przejazd=getItem(position);
                long travelId=przejazd.getPodrozId();

                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.inflate(R.menu.packlistitemmenu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.editpacklistitem:
                                Intent intent = new Intent(context, EditPrzejazdItemActivity.class);
                                intent.putExtra("przejazdId", przejazd.getId());
                                intent.putExtra("travelId",travelId);
                                context.startActivity(intent);
                                break;
                            case R.id.deletepacklistitem:
                                mDb.przejazdDao().deletePrzejazdById(przejazd.getId());
                                updateReceiptsList(mDb.przejazdDao().getPrzejazdyDlaPodrozy(travelId));
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

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {

        if(lista.size()==1){
            return ONLYONEITEM;
        }
        else if(lista.size()==2){
            if (position==0) return FIRSTITEM;
            else return LASTITEM;
        }
        else {
            if (position==0) return FIRSTITEM;
            else if (position==lista.size()-1) return LASTITEM;
            else return MIDDLEITEM;
        }
    }

    public void updateReceiptsList(List<Przejazd> newlist) {
        lista.clear();
        lista.addAll(newlist);
        notifyDataSetChanged();
    }
}
