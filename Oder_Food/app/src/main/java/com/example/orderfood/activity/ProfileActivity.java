package com.example.orderfood.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.orderfood.R;
import com.example.orderfood.retrofit.ApiBanHang;
import com.example.orderfood.retrofit.RetrofitClient;
import com.example.orderfood.utils.Utils;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtTen, txtSDT, txtDiaChi, txtEmail;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
        initControl();
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        toolbar = findViewById(R.id.toolbar);
        txtEmail = findViewById(R.id.txtEmail);
        txtSDT = findViewById(R.id.txtSDT);
        txtDiaChi = findViewById(R.id.txtDiachi);
        txtTen = findViewById(R.id.txtTen);
        if(Utils.user_current != null) {
            txtTen.setText(Utils.user_current.getFullname());
            txtSDT.setText(Utils.user_current.getPhone());
            txtDiaChi.setText(Utils.user_current.getAddress());
            txtEmail.setText(Utils.user_current.getEmail());
        }
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
