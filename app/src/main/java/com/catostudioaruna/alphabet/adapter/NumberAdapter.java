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
import com.catostudioaruna.alphabet.db.Letter;
import com.catostudioaruna.alphabet.db.Number;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.ViewHolder> {


    private List<Number> tracks;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView number;
        private FrameLayout linear;

        public ViewHolder(View view){
            super(view);
            number = (TextView) view.findViewById(R.id.number);
            linear = (FrameLayout) view.findViewById(R.id.linear);
        }
    }

    public NumberAdapter(List<Number> numberList , Context context){
        this.tracks = numberList;
        this.context = context;
    }

    @NonNull
    @Override
    public NumberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.angka,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NumberAdapter.ViewHolder holder, int position) {

        holder.number.setText((position+1 )+"");
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("number");
                intent.putExtra("index", position);
                context.sendBroadcast(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }
}
