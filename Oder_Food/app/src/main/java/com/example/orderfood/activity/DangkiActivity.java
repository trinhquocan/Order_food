package com.example.orderfood.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.orderfood.R;
import com.example.orderfood.retrofit.ApiBanHang;
import com.example.orderfood.retrofit.RetrofitClient;
import com.example.orderfood.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangkiActivity extends AppCompatActivity {

    EditText fullname, username, pass, repass, phone, email;
    AppCompatButton button;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangki);
        initView();
        initControl();

    }

    private void initControl() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangKi();
            }


        });
    }

    private void dangKi() {
        String str_username = username.getText().toString().trim();
        String str_fullname = fullname.getText().toString().trim();
        String str_email = email.getText().toString().trim();
        String str_password = pass.getText().toString().trim();
        String str_repassword = repass.getText().toString().trim();
        String str_phone = phone.getText().toString().trim();
        String str_address = null;

        // kiểm tra xem có trống không
        // Kiểm tra các trường nhập
        if (TextUtils.isEmpty(str_username)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_fullname)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập họ và tên", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_email)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_password)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
        } else if (str_password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_phone)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập số điện thoại", Toast.LENGTH_SHORT).show();
        } else if (!str_password.equals(str_repassword)) {
            Toast.makeText(getApplicationContext(), "Mật khẩu nhập lại không khớp", Toast.LENGTH_SHORT).show();
        } else {
            compositeDisposable.add(apiBanHang.dangKi(
                    str_username,
                    str_fullname,
                    str_email,
                    str_password,
                    str_phone,
                    str_address)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            userModel -> {
                                if(userModel.isSuccess()){
                                    Utils.user_current.setUsername(str_username);
                                    Utils.user_current.setPassword(str_password);
                                    Intent intent = new Intent(getApplicationContext(), DangnhapActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();

                                }
                            },
                            throwable -> {
                                Log.e("df", "dangKi: đfd" + throwable );
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    ));
        }
    }


    @SuppressLint("WrongViewCast")
    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        fullname = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        repass = findViewById(R.id.repassword);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        button = findViewById(R.id.btDangki);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
