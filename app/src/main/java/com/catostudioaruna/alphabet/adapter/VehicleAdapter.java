package com.catostudioaruna.alphabet.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.catostudioaruna.alphabet.R;
import com.catostudioaruna.alphabet.db.Vehicle;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.ViewHolder> {


    private List<Vehicle> vehicles;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView harga;
        private ImageView vehicleImage;

        private TextView pilih;

        private FrameLayout linear;

        public ViewHolder(View view){
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            pilih = (TextView) view.findViewById(R.id.beli);
            harga = (TextView) view.findViewById(R.id.price);
            vehicleImage = (ImageView) view.findViewById(R.id.vImage);
            linear = (FrameLayout) view.findViewById(R.id.linear);

        }
    }

    public VehicleAdapter(  Context context){
        this.vehicles = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.vehicle,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Vehicle v = vehicles.get(position);
        holder.name.setText(v.getName());
        holder.harga.setText(v.getPrice()+"");
        int resID = context.getResources().getIdentifier(v.getSourceName(), "drawable", context.getPackageName());

        holder.vehicleImage.setImageResource(resID);
        holder.pilih.setText("Beli "+ v.getName());
        holder.pilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("vh");
                intent.putExtra("vid",v.getId());
                intent.putExtra("rid",resID);
                context.sendBroadcast(intent);
            }
        });
    }

    public void setItem(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }
}
