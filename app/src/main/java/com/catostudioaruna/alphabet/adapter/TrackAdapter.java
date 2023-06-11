package com.catostudioaruna.alphabet.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.catostudioaruna.alphabet.db.Letter;
import com.catostudioaruna.alphabet.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrackAdapter  extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {

    private List<Letter> tracks;
    private Context context;



    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView album;
        private TextView number;
        private LinearLayout linear;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.song);
            album = (TextView) itemView.findViewById(R.id.album);
            number = (TextView) itemView.findViewById(R.id.number);
            linear = (LinearLayout) itemView.findViewById(R.id.linear);
        }
    }

    public TrackAdapter(List<Letter> tracks, Context context) {
        this.tracks = tracks;
        this.context = context;
    }

    @NonNull
    @Override
    public TrackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.track,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrackAdapter.ViewHolder holder, int position) {
        holder.title.setText(tracks.get(position).getTitle());
        holder.number.setText((position+1 )+"");
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("track");
                intent.putExtra("index", position);
                context.sendBroadcast(intent);
            }
        });



    }

    @Override
    public int getItemCount()
    {

        return tracks.size();
    }
}
