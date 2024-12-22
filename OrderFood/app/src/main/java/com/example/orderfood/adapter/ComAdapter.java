package com.example.orderfood.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.orderfood.R;
import com.example.orderfood.model.SpBanChay;

import java.util.List;

public class
ComAdapter extends RecyclerView.Adapter<ComAdapter.MyViewHolder> {
    Context context;
    List<SpBanChay> array;

    public ComAdapter(Context context, List<SpBanChay> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_com,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SpBanChay spBanChay = array.get(position);
        holder.tensp.setText(spBanChay.getTENMONAN());
        holder.giasp.setText(spBanChay.getGIA());
        holder.mota.setText(spBanChay.getMOTA());
        Glide.with(context).load(spBanChay.getHINHANH()).into(holder.hinhanh);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tensp, giasp, mota;
        ImageView hinhanh;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tensp = itemView.findViewById(R.id.itemcom_ten);
            giasp = itemView.findViewById(R.id.itemcom_gia);
            mota = itemView.findViewById(R.id.itemcom_mota);
            hinhanh = itemView.findViewById(R.id.itemcom_image);
        }
        // ... các biến thành viên và constructor
    }
}
