package com.example.lukasz.apptravel.notatkilisttools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.activities.EditNotatkiActivity;
import com.example.lukasz.apptravel.activities.EditPrzejazdItemActivity;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.Notatka;
import com.example.lukasz.apptravel.db.entities.Przejazd;

import org.w3c.dom.Text;

import java.util.List;

public class NotatkiListAdapter extends RecyclerView.Adapter<NotatkiListAdapter.MyViewHolder> {

    private Context context;
    private List<Notatka> mData;
    private Uri uri;
    private AppDatabase mDb;


    public NotatkiListAdapter(Context context, List<Notatka> mData){
        this.context=context;
        this.mData=mData;
        mDb=AppDatabase.getInstance(context);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(R.layout.notatkacardview,parent,false);



        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.notatkaName.setText(mData.get(i).getTytul());
        if (mData.get(i).getZdjecieUri()==null){
            myViewHolder.notatkaImage.setImageResource(R.drawable.nophotoimage);
        }
        else{
            uri=Uri.parse(mData.get(i).getZdjecieUri());
         //   context.grantUriPermission(context.getPackageName(),uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
         //   final int takeFlags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION);
         //   context.getContentResolver().takePersistableUriPermission(uri, takeFlags);
            Glide.with(context).load(uri).apply(new RequestOptions().override(300, 200)).into(myViewHolder.notatkaImage);
        }

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, EditNotatkiActivity.class);
                intent.putExtra("notatkaId", mData.get(i).getId());
                intent.putExtra("travelId", mData.get(i).getPodrozId());
                context.startActivity(intent);
            }
        });

        myViewHolder.notatkaMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.inflate(R.menu.packlistitemmenu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.editpacklistitem:
                                Intent intent = new Intent(context, EditNotatkiActivity.class);
                                intent.putExtra("notatkaId",mData.get(i).getId());
                                intent.putExtra("travelId",mData.get(i).getPodrozId());
                                context.startActivity(intent);
                                break;
                            case R.id.deletepacklistitem:
                                mDb.notatkaDao().deleteNotatkaById(mData.get(i).getId());
                                updateReceiptsList(mDb.notatkaDao().getNotatkiByTravelId(mData.get(i).getPodrozId()));
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public void updateReceiptsList(List<Notatka> newlist) {
        mData.clear();
        mData.addAll(newlist);
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView notatkaName;
        ImageView notatkaImage;
        TextView notatkaMenu;
        CardView cardView;

        public MyViewHolder(View view){
            super(view);
            notatkaImage=view.findViewById(R.id.notatkaitemzdj);
            notatkaName=view.findViewById(R.id.notatkaitemname);
            notatkaMenu=view.findViewById(R.id.notatkamenu);
            cardView=view.findViewById(R.id.notatkaitem);

        }
    }


}
