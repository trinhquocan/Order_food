package com.example.orderfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

public class KhacActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    //    int page = 1;
//    int loai;
    ComAdapter AdapterKhac;
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
        setContentView(R.layout.activity_com);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
//        loai = getIntent().getIntExtra("LOAI",1);
        AnhXa();
        ActionToolBar();
        getData();
    }

//

    private void ActionToolBar() {
        setSupportActionBar(toolbar);  // Thiết lập Toolbar làm ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Hiển thị nút quay lại
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getData() {
        compositeDisposable.add(apiBanHang.getSpBanChay3()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        spBanChayModel  -> {
                            if(spBanChayModel.isSuccess()){
                                spBanChayList = spBanChayModel.getResult();
                                AdapterKhac = new ComAdapter(getApplicationContext(), spBanChayList);
                                recyclerView.setAdapter(AdapterKhac);
                            }
                        },
                        throwable -> {
                            Log.e("loggg", "Error fetching data", throwable);
                            Toast.makeText(getApplicationContext(),"khong ket noi server", Toast.LENGTH_LONG).show();
                        }
                ));
    }



    private void AnhXa() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview_com);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        spBanChayList = new ArrayList<>();
        frameLayout = findViewById(R.id.framegiohang); // ID của layout giỏ hàng
        imgSearch = findViewById(R.id.imgsearch);  // ID của biểu tượng tìm kiếm
        badge = findViewById(R.id.menu_sl);

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