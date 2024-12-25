package com.example.orderfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.orderfood.R;
import com.example.orderfood.model.GioHang;
import com.example.orderfood.model.SpBanChay;
import com.example.orderfood.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

public class ChiTietActivity extends AppCompatActivity {
    TextView tensp, giasp, mota;
    Button btnthemgh;
    ImageView imghinhanh;
    Spinner spinner;
    Toolbar toolbar;
    SpBanChay sanPhamMoi;
    NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);

        initView();
        ActionToolbar();
        initData();
        initControl();

    }

    private void initControl() {
        btnthemgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themGioHang();

            }


        });

    }
    private void themGioHang() {
        if (Utils.manggiohang.size() > 0){
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            boolean flag = false;
            for (int i = 0; i < Utils.manggiohang.size(); i++) {
                if (Utils.manggiohang.get(i).getIdsp() == sanPhamMoi.getMAMONAN()){
                    Utils.manggiohang.get(i).setSoluong(soluong + Utils.manggiohang.get(i).getSoluong());
                    long gia = Long .parseLong(sanPhamMoi.getGIA()) * Utils.manggiohang.get(i).getSoluong();
                    Utils.manggiohang.get(i).setGiasp(gia);
                    flag = true;
                }

            }
            if (flag == false){
                long gia = Long.parseLong(sanPhamMoi.getGIA()) * soluong;
                GioHang gioHang = new GioHang();
                gioHang.setGiasp(gia);
                gioHang.setIdsp(sanPhamMoi.getMAMONAN());
                gioHang.setSoluong(soluong);
                gioHang.setTensp(sanPhamMoi.getTENMONAN());
                gioHang.setHinhsp(sanPhamMoi.getHINHANH());
                Utils.manggiohang.add(gioHang);
            }
        }else {
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            long gia = Long.parseLong(sanPhamMoi.getGIA()) * soluong;
            GioHang gioHang = new GioHang();
            gioHang.setGiasp(gia);
            gioHang.setIdsp(sanPhamMoi.getMAMONAN());
            gioHang.setSoluong(soluong);
            gioHang.setTensp(sanPhamMoi.getTENMONAN());
            gioHang.setHinhsp(sanPhamMoi.getHINHANH());
            Utils.manggiohang.add(gioHang);
        }
        int totalItem = 0;
        for (int i = 0; i < Utils.manggiohang.size(); i++) {
            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
        }

        badge.setText(String.valueOf(totalItem));
    }

    private void initData() {
        sanPhamMoi = (SpBanChay) getIntent().getSerializableExtra("chitiet");
        tensp.setText(sanPhamMoi.getTENMONAN());
        mota.setText(sanPhamMoi.getMOTA());
        Glide.with(getApplicationContext()).load(sanPhamMoi.getHINHANH()).into(imghinhanh);
        long gia = Long.parseLong(sanPhamMoi.getGIA()); // Chuyển giá thành số
        // Định dạng giá thành dạng ###,###,### và thêm "đ" vào cuối
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedGia = "Giá: " + formatter.format(gia) + " đ";
        giasp.setText(formattedGia);
        Integer[] sol= new Integer[]{1, 2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sol);
        spinner.setAdapter(adapterspin);
    }

    private void initView() {
        tensp = findViewById(R.id.txttensp);
        giasp = findViewById(R.id.txtgiasp);
        mota = findViewById(R.id.txtmotachitiet);
        btnthemgh = findViewById(R.id.btnthemvaogiohang);
        spinner = findViewById(R.id.spinner);
        imghinhanh = findViewById(R.id.imgchitiet);
        toolbar = findViewById(R.id.toolbar);
        FrameLayout frameLayoutgiohang = findViewById(R.id.framegiohang);
        frameLayoutgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });


        badge = findViewById(R.id.menu_sl);

        if (Utils.manggiohang != null){
            int totalItem = 0;
            for (int i = 0; i < Utils.manggiohang.size(); i++) {
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }

            badge.setText(String.valueOf(totalItem));
        }


    }


    private void ActionToolbar() {
        setSupportActionBar(toolbar);  // Thiết lập Toolbar làm ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Hiển thị nút quay lại
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.manggiohang != null){
            int totalItem = 0;
            for (int i = 0; i < Utils.manggiohang.size(); i++) {
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }

            badge.setText(String.valueOf(totalItem));
        }

    }
}