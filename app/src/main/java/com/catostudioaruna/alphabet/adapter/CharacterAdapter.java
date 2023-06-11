package com.catostudioaruna.alphabet.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.catostudioaruna.alphabet.R;
import com.catostudioaruna.alphabet.db.Character;
import com.catostudioaruna.alphabet.db.Number;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {


    private List<Character> characters;
    private Context context;
    private int characterId;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView skill1;
        private TextView skill2;
        private ImageView characterImage;
        private ImageView skill1Value;
        private ImageView skill2Value;

        private TextView pilih;

        private LinearLayout linear;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            pilih = (TextView) view.findViewById(R.id.pilih);
            skill1 = (TextView) view.findViewById(R.id.skill1);
            skill2 = (TextView) view.findViewById(R.id.skill2);
            characterImage = (ImageView) view.findViewById(R.id.imageCh);
            skill1Value = (ImageView) view.findViewById(R.id.skill1Value);
            skill2Value = (ImageView) view.findViewById(R.id.skill2value);

            linear = (LinearLayout) view.findViewById(R.id.linear);

        }
    }

    public CharacterAdapter(Context context) {
        this.context = context;
        characters = new ArrayList<>();
    }

    public void setItem(List<Character> c) {

        this.characters = c;

        notifyDataSetChanged();
    }

    public void myCurrentProfileSetting(int characterId){
        this.characterId = characterId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.character, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Character c = characters.get(position);
        holder.name.setText(c.getName());
        holder.pilih.setText("Pilih " + c.getName() );
        int resID = context.getResources().getIdentifier(c.getResourceName(), "drawable", context.getPackageName());
        holder.characterImage.setImageResource(resID);
        holder.skill1.setText(c.getSkills().get(0).getName());
        holder.skill2.setText(c.getSkills().get(1).getName());
        holder.skill1Value.setMinimumWidth(c.getSkillJoinCharacter().get(0).getValue());
        holder.skill2Value.setMinimumWidth(c.getSkillJoinCharacter().get(1).getValue());

        holder.pilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("US");
                intent.putExtra("uid",c.getId());
                intent.putExtra("rid",resID);
                context.sendBroadcast(intent);

            }
        });


    }



    @Override
    public int getItemCount() {
        return characters.size();
    }

    /*public void updateUI() {
        holder.skill1.setText(c.getSkills().get(0).getName());
        holder.skill2.setText(c.getSkills().get(1).getName());
        holder.skill1Value.setMinimumWidth(c.getSkillJoinCharacter().get(0).getValue());
        holder.skill2Value.setMinimumWidth(c.getSkillJoinCharacter().get(1).getValue());
    }*/
}
