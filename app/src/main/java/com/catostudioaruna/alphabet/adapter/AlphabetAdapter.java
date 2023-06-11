package com.catostudioaruna.alphabet.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.catostudioaruna.alphabet.R;
import com.catostudioaruna.alphabet.db.Fruit;
import com.catostudioaruna.alphabet.db.Huruf;
import com.catostudioaruna.alphabet.db.Number;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlphabetAdapter extends RecyclerView.Adapter<AlphabetAdapter.ViewHolder> {

    private List<Huruf> tracks;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView huruf;
        private LinearLayout layout;

        public ViewHolder(View view){
            super(view);
            huruf = (TextView) view.findViewById(R.id.huruf);

            layout = (LinearLayout) view.findViewById(R.id.linear);
        }
    }

    public AlphabetAdapter(Context context) {
        tracks = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.huruf,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Huruf h = tracks.get(position);
        holder.huruf.setText(tracks.get(position).getIconName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("huruf");
                intent.putExtra("index", position);
                intent.putExtra("rname", h.getSourceName());
                intent.putExtra("name", h.getName());
                intent.putExtra("audio", h.getAudio());
                intent.putExtra("id",h.getId());
                intent.putExtra("iconName",h.getIconName());


                context.sendBroadcast(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }


    public void setTracks(List<Huruf> list) {
        this.tracks = list;
        notifyDataSetChanged();
    }
}
