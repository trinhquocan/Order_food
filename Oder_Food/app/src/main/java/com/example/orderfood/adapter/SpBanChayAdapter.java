package com.example.orderfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderfood.Interface.ItemClickListener;
import com.example.orderfood.R;
import com.example.orderfood.activity.ChiTietActivity;
import com.example.orderfood.model.SpBanChay;

import java.text.DecimalFormat;
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
        Glide.with(context).load(spBanChay.getHINHANH()).into(holder.imagehinhanh);

        long gia = Long.parseLong(spBanChay.getGIA()); // Chuyển giá thành số
// Định dạng giá thành dạng ###,###,### và thêm "đ" vào cuối
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedGia = formatter.format(gia) + " đ";
        holder.txtgia.setText(formattedGia);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if (!isLongClick) {
                    //click
                    Intent intent = new Intent(context, ChiTietActivity.class);
                    intent.putExtra("chitiet", spBanChay);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    // Lớp ViewHolder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView  txtten;
        TextView txtgia;
        ImageView imagehinhanh;
        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtgia = itemView.findViewById(R.id.itemsp_gia);
            txtten = itemView.findViewById(R.id.itemsp_ten);
            imagehinhanh = itemView.findViewById(R.id.itemsp_image);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }
    }
}
