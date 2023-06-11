package com.catostudioaruna.alphabet.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.catostudioaruna.alphabet.R;
import com.catostudioaruna.alphabet.db.Level;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {

    private List<Level> levels;
    private Context context;
    private int lastLevel;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View line;
        TextView levelName;
        ImageView levelColor;
        RecyclerView badges;
        ImageView badge1;
        ImageView badge2;
        LinearLayout badgeGang;

        public ViewHolder(View view) {
            super(view);
            levelName = (TextView) view.findViewById(R.id.levelName);
            levelColor = view.findViewById(R.id.levelColor);
            badges = view.findViewById(R.id.badges);
            line = view.findViewById(R.id.line);
            badge1 = view.findViewById(R.id.badge1);
            badge2 = view.findViewById(R.id.badge2);
            badgeGang = view.findViewById(R.id.badgeGang);
        }
    }

    public LevelAdapter(Context context) {
        this.context = context;
        levels = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.level, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Level l = levels.get(position);
        holder.levelName.setText(l.getName());
        Log.i("LEVEL","id : " + l.getId()+"    last level : "+ lastLevel);

        if(l.getId() <= lastLevel){
                holder.levelName.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                holder.line.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                int resGreyCircle = context.getResources().getIdentifier("i_badge_star", "drawable", context.getPackageName());
                holder.levelColor.setImageResource(resGreyCircle);

        }




        if (l.getBadges() != null && l.getBadges().size() > 0) {
            boolean badges1Status = l.getBadges().get(0).isAchieved();
            boolean badges2Status = l.getBadges().get(1).isAchieved();

            if (badges1Status) {
                int resID1 = context.getResources().getIdentifier(l.getBadges().get(0).getIconSourceName(), "drawable", context.getPackageName());
                holder.badge1.setImageResource(resID1);
            }

            if (badges2Status) {
                int resID2 = context.getResources().getIdentifier(l.getBadges().get(1).getIconSourceName(), "drawable", context.getPackageName());
                holder.badge2.setImageResource(resID2);
            }

        }

        if(l.getId() == 5){
            holder.badgeGang.setVisibility(View.GONE);
            holder.line.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        Log.i("level", "level  = " + levels.size());
        return levels.size();

    }

    public List<Level> getLevels() {
        return levels;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
        notifyDataSetChanged();
    }

    public void setLastLevel(int lastLevel){
        this.lastLevel = lastLevel;
        notifyDataSetChanged();
    }
}
