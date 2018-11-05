package com.example.lukasz.apptravel.notatkilisttools;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.entities.Notatka;

import org.w3c.dom.Text;

import java.util.List;

public class NotatkiListAdapter extends RecyclerView.Adapter<NotatkiListAdapter.MyViewHolder> {

    private Context context;
    private List<Notatka> mData;
    private Uri uri;

    public NotatkiListAdapter(Context context, List<Notatka> mData){
        this.context=context;
        this.mData=mData;
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
            Glide.with(context).load(uri).into(myViewHolder.notatkaImage);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView notatkaName;
        ImageView notatkaImage;
        public MyViewHolder(View view){
            super(view);
            notatkaImage=view.findViewById(R.id.notatkaitemzdj);
            notatkaName=view.findViewById(R.id.notatkaitemname);
        }
    }
}
