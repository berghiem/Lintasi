package com.catostudioaruna.alphabet.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.catostudioaruna.alphabet.AlphabetAppUtil;
import com.catostudioaruna.alphabet.R;
import com.catostudioaruna.alphabet.db.Animal;
import com.catostudioaruna.alphabet.db.Huruf;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.ViewHolder> {

    private List<Animal> tracks;
    private Context context;
    private AlphabetAppUtil appUtil;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView binatang;
        private LinearLayout linear;

        public ViewHolder(View view){
            super(view);
            binatang = (ImageView) view.findViewById(R.id.object);
            linear = (LinearLayout) view.findViewById(R.id.linear);
        }
    }

    public AnimalAdapter(Context context) {
        this.tracks = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.hewan,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Animal a =  tracks.get(position);
        int resID = context.getResources().getIdentifier(a.getIconSourceName(), "drawable", context.getPackageName());
        holder.binatang.setImageResource(resID);
        //appUtil.setImage(holder.binatang,resID);


        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("animal");
                intent.putExtra("index", position);
                intent.putExtra("rname", a.getSourceName());
                intent.putExtra("name", a.getName());
                intent.putExtra("type", a.getType());
                intent.putExtra("audio", a.getAudio());
                intent.putExtra("sourceId", a.getSourceId());
                intent.putExtra("id",a.getId());
                context.sendBroadcast(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public void setTracks(List<Animal> tracks) {
        this.tracks = tracks;
        notifyDataSetChanged();
    }

    public void setAppUtil(AlphabetAppUtil appUtil){
        this.appUtil = appUtil;
    }


}
