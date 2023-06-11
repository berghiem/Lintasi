package com.catostudioaruna.alphabet.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.catostudioaruna.alphabet.AlphabetAppUtil;
import com.catostudioaruna.alphabet.R;
import com.catostudioaruna.alphabet.db.Animal;
import com.catostudioaruna.alphabet.db.Fruit;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private List<Fruit> fruitList;
    private Context context;
    private AlphabetAppUtil appUtil;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView buah;

        public ViewHolder(View view){
            super(view);
            buah = (ImageView) view.findViewById(R.id.object);
        }
    }

    public FruitAdapter(Context context) {
        this.fruitList = new ArrayList<>();
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
        Fruit a =  fruitList.get(position);
        int resID = context.getResources().getIdentifier(a.getIconSourceName(), "drawable", context.getPackageName());
        holder.buah.setImageResource(resID);
       // appUtil.setImage(holder.buah, resID);

        holder.buah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("animal");
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
        return fruitList.size();
    }

    public void setTracks(List<Fruit> tracks) {
        this.fruitList = tracks;
        notifyDataSetChanged();
    }

    public void setAppUtil(AlphabetAppUtil appUtil){
        this.appUtil = appUtil;
    }

}
