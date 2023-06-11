package com.catostudioaruna.alphabet.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.catostudioaruna.alphabet.AlphabetAppUtil;
import com.catostudioaruna.alphabet.R;
import com.catostudioaruna.alphabet.db.Fruit;
import com.catostudioaruna.alphabet.db.Seasonal;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SeasonalAdapter extends RecyclerView.Adapter<SeasonalAdapter.ViewHolder> {

    private List<Seasonal> seasonals;
    private Context context;
    private AlphabetAppUtil appUtil;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView seasonalImage;

        public ViewHolder(View view){
            super(view);
            seasonalImage = (ImageView) view.findViewById(R.id.object);
        }
    }

    public SeasonalAdapter(Context context) {
        this.seasonals = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.buah,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Seasonal a =  seasonals.get(position);
        int resID = context.getResources().getIdentifier(a.getIconSourceName(), "drawable", context.getPackageName());
        holder.seasonalImage.setImageResource(resID);
        // appUtil.setImage(holder.buah, resID);

        holder.seasonalImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("seasonal");
                intent.putExtra("index", position);
                intent.putExtra("rname", a.getSourceName());
                intent.putExtra("name", a.getName());
                intent.putExtra("audio", a.getAudio());
                intent.putExtra("id",a.getId());
                context.sendBroadcast(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return seasonals.size();
    }

    public void setTracks(List<Seasonal> tracks) {
        this.seasonals = tracks;
        notifyDataSetChanged();
    }

    public void setAppUtil(AlphabetAppUtil appUtil){
        this.appUtil = appUtil;
    }

}

