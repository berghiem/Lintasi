package com.catostudioaruna.alphabet.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.catostudioaruna.alphabet.R;
import com.catostudioaruna.alphabet.db.Sticker;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {

    private List<Sticker> stickers;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView sticker;
        private TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            sticker = (ImageView) itemView.findViewById(R.id.sticker_image);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    public StickerAdapter() {
        this.stickers = new ArrayList<>();

    }

    @NonNull
    @Override
    public StickerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.sticker, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StickerAdapter.ViewHolder holder, int position) {
        holder.sticker.setImageResource(stickers.get(position).getSourceId());

        holder.text.setText(stickers.get(position).getName() +  " ," + stickers.get(position).getName() + stickers.get(position).getSourceId());

    }

    @Override
    public int getItemCount() {
        return stickers.size();
    }

    public List<Sticker> getStickers() {
        return stickers;
    }

    public void setStickers(List<Sticker> stickers) {
        this.stickers = stickers;
        notifyDataSetChanged();
    }
}
