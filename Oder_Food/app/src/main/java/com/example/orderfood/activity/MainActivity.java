package com.example.orderfood.activity;
import android.content.Context;
import android.content.Intent;
import android.icu.util.ULocale;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.bumptech.glide.Glide;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.orderfood.R;
import com.example.orderfood.adapter.LoaiSpAdapter;
import com.example.orderfood.adapter.SpBanChayAdapter;
import com.example.orderfood.model.LoaiSp;
import com.example.orderfood.model.SpBanChay;
import com.example.orderfood.retrofit.ApiBanHang;
import com.example.orderfood.retrofit.RetrofitClient;
import com.example.orderfood.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    LoaiSpAdapter loaiSpAdapter;
    List<LoaiSp> mangloaisp;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    List<SpBanChay> mangSpBanChay;
    SpBanChayAdapter spBanChayAdapter;
    NotificationBadge badge;
    FrameLayout frameLayout;
    ImageView imgsearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        Anhxa();
        ActionBar();

        if(isConnected(this)){
            ActionViewFlipper();
            getLoaiSanPham();
            getSpBanChay();
            getEvenClick();

        }else
        {
            Toast.makeText(getApplicationContext(), "khong co ket noi, vui long kiem tra lai", Toast.LENGTH_LONG).show();
        }
    }

    private void getEvenClick() {
        listViewManHinhChinh.setOnItemClickListener((adapterView, view, i, l) -> {
            switch (i) {
                case 0:
                    Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(trangchu);
                    break;

                case 1:
                    Intent com = new Intent(getApplicationContext(), ComActivity.class);
//                    com.putExtra("LOAI",3);
                    startActivity(com);
                    break;
                case 2:
                    Intent pho = new Intent(getApplicationContext(), PhoActivity.class);
                    startActivity(pho);
//                    pho.putExtra("LOAI",4);

                    break;
                case 3:
                    Intent lienhe = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(lienhe);
                    break;
            }
        });
    }


    private void getSpBanChay() {
        compositeDisposable.add(apiBanHang.getSpBanChay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        SpBanChayModel -> {
                            if(SpBanChayModel.isSuccess()){
                                mangSpBanChay = SpBanChayModel.getResult();
                                spBanChayAdapter = new SpBanChayAdapter(getApplicationContext(),mangSpBanChay);
                                recyclerViewManHinhChinh.setAdapter(spBanChayAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối với server" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void getLoaiSanPham() {
         compositeDisposable.add(
                 apiBanHang.getLoaiSp()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                loaiSpModel -> {
                                    if (loaiSpModel.isSuccess())
                                    {
                                        mangloaisp = loaiSpModel.getResult();
                                        loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(),mangloaisp);
                                        listViewManHinhChinh.setAdapter(loaiSpAdapter);
                                    }
                                }

                            ));

    }


private void ActionViewFlipper() {
    List<String> mangquangcao = new ArrayList<>();
    mangquangcao.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png");
    mangquangcao.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg");

    for (String url : mangquangcao) {
        ImageView imageView = new ImageView(getApplicationContext());
        Glide.with(getApplicationContext())
                .load(url)
                .override(800, 300) // Resize ảnh
                .into(imageView);

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        viewFlipper.addView(imageView);
    }

    viewFlipper.setFlipInterval(5000); // Thời gian giữa các ảnh (ms)
    viewFlipper.setAutoStart(true);   // Bắt đầu tự động

    Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
    Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);

    viewFlipper.setInAnimation(slide_in);
    viewFlipper.setOutAnimation(slide_out);
}


    private void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private void Anhxa(){
        imgsearch = findViewById(R.id.imgsearch);
        toolbar   = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewlipper);
        recyclerViewManHinhChinh = findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        navigationView = findViewById(R.id.navigationview);
        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = findViewById(R.id.drawerlayout);
        // khoi tao list
        mangloaisp = new ArrayList<>();
        mangSpBanChay = new ArrayList<>();
        badge = findViewById(R.id.menu_sl);
        frameLayout = findViewById(R.id.framegiohang);

            if (Utils.manggiohang == null) {
                Utils.manggiohang = new ArrayList<>();

            } else {
                int totalItem = 0;
                for (int i = 0; i < Utils.manggiohang.size(); i++) {
                    totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
                } badge.setText(String.valueOf(totalItem));
            }

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });

        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent search = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(search);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int totalItem = 0;
        for (int i = 0; i < Utils.manggiohang.size(); i++) {
            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
        }

        badge.setText(String.valueOf(totalItem));

    }


    // kiem tra ket noi internet
    private boolean isConnected (Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())){
            return true;
        }else{
            return false;
        }
    }
    @Override
    protected void onDestroy()
    {
        compositeDisposable.clear();
        super.onDestroy();
    }
}