package com.example.orderfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.R;
import com.example.orderfood.adapter.ComAdapter;
import com.example.orderfood.model.SpBanChay;
import com.example.orderfood.retrofit.ApiBanHang;
import com.example.orderfood.retrofit.RetrofitClient;
import com.example.orderfood.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PhoActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ComAdapter adapterPho;
    List<SpBanChay> spBanChayList;
    LinearLayoutManager linearLayoutManager;
    Handler handler;
    boolean isLoading = false;
    FrameLayout frameLayout;
    ImageView imgSearch;
    NotificationBadge badge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pho); // Đổi tên layout thành activity_pho
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        AnhXa();
        ActionToolBar();
        getData();

    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getData() {
        compositeDisposable.add(apiBanHang.getSpBanChay1()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        spBanChayModel -> {
                            if (spBanChayModel.isSuccess()) {
                                spBanChayList = spBanChayModel.getResult();
                                adapterPho = new ComAdapter(getApplicationContext(), spBanChayList); // Sử dụng PhoAdapter
                                recyclerView.setAdapter(adapterPho);
                            }
                        },
                        throwable -> {
                            Log.e("loggg", "Error fetching data", throwable);
                            Toast.makeText(getApplicationContext(), "Không kết nối server", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview_pho); // Đổi ID RecyclerView
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        spBanChayList = new ArrayList<>();
        frameLayout = findViewById(R.id.framegiohang); // ID của layout giỏ hàng
        imgSearch = findViewById(R.id.imgsearch);  // ID của biểu tượng tìm kiếm
        badge = findViewById(R.id.menu_sl);

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
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

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
