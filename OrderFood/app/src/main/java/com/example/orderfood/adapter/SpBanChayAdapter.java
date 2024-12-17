package com.example.orderfood.adapter;

import android.content.Context;
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

public class SpBanChayAdapter extends RecyclerView.Adapter<SpBanChayAdapter.MyViewHolder> {
    Context context;
    List<SpBanChay> array;

    // Constructor
    public SpBanChayAdapter(Context context, List<SpBanChay> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanphambanchay, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        SpBanChay spBanChay = array.get(position);
        holder.txtten.setText(spBanChay.getTENMONAN());
        holder.txtgia.setText(spBanChay.getGIA());
        Glide.with(context).load(spBanChay.getHINHANH()).into(holder.imagehinhanh);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    // Lớp ViewHolder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  txtten;
        TextView txtgia;
        ImageView imagehinhanh;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtgia = itemView.findViewById(R.id.itemsp_gia);
            txtten = itemView.findViewById(R.id.itemsp_ten);
            imagehinhanh = itemView.findViewById(R.id.itemsp_image);
        }
    }
}