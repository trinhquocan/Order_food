
package com.example.orderfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderfood.R;
import com.example.orderfood.retrofit.ApiBanHang;
import com.example.orderfood.retrofit.RetrofitClient;
import com.example.orderfood.utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangnhapActivity extends AppCompatActivity {
    EditText username, password;
    TextView txtdangki;
    Button btDangnhap;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        initView();
        initControl();
    }



    private void dangNhap(String username,String pass) {
        compositeDisposable.add(apiBanHang.dangNhap(username,pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()) {
                                isLogin = true;
                                Paper.book().write("isLogin", isLogin);


                                Utils.user_current = userModel.getResult().get(0);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        },
                        throwable -> {
                            Log.e("Lỗi", "onClick: ", throwable );
                        }
                ));

    }
    private void initView() {
        Paper.init(this);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        txtdangki = findViewById(R.id.txtdangki);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btDangnhap = findViewById(R.id.btDangnhap);

        if(Paper.book().read("username") != null && Paper.book().read("pass") != null) {
            username.setText(Paper.book().read("username"));
            password.setText(Paper.book().read("pass"));
            if(Paper.book().read("isLogin") != null){
                boolean flag = Paper.book().read("isLogin");
                if(flag){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dangNhap(Paper.book().read("username"),Paper.book().read("pass"));
                        }
                    },1000);
                }
            }
        }

    }
    private void initControl() {
        txtdangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DangkiActivity.class);
                startActivity(intent);
            }
        });
        btDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_username = username.getText().toString().trim();
                String str_password = password.getText().toString().trim();
                if (TextUtils.isEmpty(str_username)) {
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(str_password)) {
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    Paper.book().write("username", str_username);
                    Paper.book().write("pass", str_password);
                    dangNhap(str_username,str_password);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.user_current.getUsername() != null && Utils.user_current.getPassword() != null) {
            username.setText(Utils.user_current.getUsername());
            password.setText(Utils.user_current.getPassword());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
