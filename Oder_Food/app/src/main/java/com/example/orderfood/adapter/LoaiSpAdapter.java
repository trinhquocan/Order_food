package com.example.orderfood.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.orderfood.R;
import com.example.orderfood.model.LoaiSp;
import java.util.List;
public class LoaiSpAdapter extends BaseAdapter {
    List<LoaiSp> array;
    Context context;

    public LoaiSpAdapter(Context context, List<LoaiSp> array) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder{
        TextView textensp;
        ImageView imghinhanh;
    }

@Override
public View getView(int i, View view, ViewGroup viewGroup) {
    ViewHolder viewHolder;
    if (view == null) {
        viewHolder = new ViewHolder();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.item_sanpham, null);
        viewHolder.textensp = view.findViewById(R.id.item_tensp);
        viewHolder.imghinhanh = view.findViewById(R.id.item_image);
        view.setTag(viewHolder);
    } else {
        viewHolder = (ViewHolder) view.getTag();
    }

    // Reset dữ liệu để tránh dữ liệu cũ còn sót lại
    viewHolder.textensp.setText("");
    viewHolder.imghinhanh.setImageResource(0);

    // Đặt giá trị cho ViewHolder
    LoaiSp loaiSp = array.get(i);
    if (loaiSp.getMALOAI() != null) {
        viewHolder.textensp.setText(loaiSp.getTENMONAN());
    } else {
        viewHolder.textensp.setText("N/A");
    }

    Glide.with(context)
            .load(loaiSp.getHINHANH())
            .placeholder(R.drawable.image_24)

            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(viewHolder.imghinhanh);
    return view;
    }
}
