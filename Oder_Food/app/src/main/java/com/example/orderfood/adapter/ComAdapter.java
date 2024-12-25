package com.example.orderfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class ComAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<SpBanChay> array;

    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public ComAdapter(Context context, List<SpBanChay> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       if (viewType == VIEW_TYPE_DATA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_com, parent,false);
            return new MyViewHolder(view);
       } else {
           View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent,false);
           return new MyViewHolder(view);
       }
    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_DATA;
    }

    //    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof MyViewHolder) {
//            MyViewHolder myViewHolder = (MyViewHolder) holder;
//            SpBanChay spBanChay = array.get(position);
//            myViewHolder.tensp.setText(spBanChay.getTENMONAN());
//            myViewHolder.giasp.setText(spBanChay.getGIA());
//            long gia = Long.parseLong(spBanChay.getGIA()); // Chuyển giá thành số
//            // Định dạng giá thành dạng ###,###,### và thêm "đ" vào cuối
//            DecimalFormat formatter = new DecimalFormat("#,###,###");
//            String formattedGia = formatter.format(gia) + " đ";
//            myViewHolder.giasp.setText(formattedGia);
//            myViewHolder.mota.setText(spBanChay.getMOTA());
//            Glide.with(context).load(spBanChay.getHINHANH()).into(myViewHolder.hinhanh);
//
////            myViewHolder.setItemClickListener (new)
//        }else {
//            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
//            loadingViewHolder.progressBar.setIndeterminate(true);
//        }
//    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            SpBanChay sanPham = array.get(position);
            myViewHolder.tensp.setText(sanPham.getTENMONAN());
            long gia = Long.parseLong(sanPham.getGIA()); // Chuyển giá thành số
// Định dạng giá thành dạng ###,###,### và thêm "đ" vào cuối
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String formattedGia = formatter.format(gia) + " đ";
            myViewHolder.giasp.setText(formattedGia);
            myViewHolder.mota.setText(sanPham.getMOTA());
            Glide.with(context).load(sanPham.getHINHANH()).into(myViewHolder.hinhanh);

            myViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int pos, boolean isLongClick) {
                    if (!isLongClick) {
                        //click
                        Intent intent = new Intent(context, ChiTietActivity.class);
                        intent.putExtra("chitiet", sanPham);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                }
            });
        } else {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }



    @Override
    public int getItemCount() {
        return array.size();
    }

    public class LoadingViewHolder extends  RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tensp, giasp, mota;
        ImageView hinhanh;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tensp = itemView.findViewById(R.id.itemcom_ten);
            giasp = itemView.findViewById(R.id.itemcom_gia);
            mota = itemView.findViewById(R.id.itemcom_mota);
            hinhanh = itemView.findViewById(R.id.itemcom_image);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }


        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);

        }
        // ... các biến thành viên và constructor
    }


}
