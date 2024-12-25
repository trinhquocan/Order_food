package com.example.orderfood.activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.security.identity.InvalidRequestMessageException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
public class ComActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
//    int page = 1;
//    int loai;
    ComAdapter AdapterCom;
    List<SpBanChay> spBanChayList;
    LinearLayoutManager linearLayoutManager;
    Handler handler;
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
//        loai = getIntent().getIntExtra("LOAI",1);
            AnhXa();
            ActionToolBar();
            getData();
//            addEventLoad();
    getData();
        }

//    private void addEventLoad() {
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (isLoading == false){
//                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == spBanChayList.size()-1){
//                        isLoading = true;
////                        loadMore();
//                    }
//                }
//            }
//
//
//        });
//
//    }
//    private void loadMore() {
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                spBanChayList.add(null);
//                AdapterCom.notifyItemInserted(spBanChayList.size()-1);
//            }
//        });
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                spBanChayList.remove(spBanChayList.size()-1);
//                AdapterCom.notifyItemRemoved(spBanChayList.size());
//                page = page+1;
////                getData(page);
//                getData();
//                AdapterCom.notifyDataSetChanged();
//                isLoading = false;
//            }
//        }, 2000);
//    }

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
        compositeDisposable.add(apiBanHang.getSpBanChay2()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    spBanChayModel  -> {
                        if(spBanChayModel.isSuccess()){
                            spBanChayList = spBanChayModel.getResult();
                            AdapterCom = new ComAdapter(getApplicationContext(), spBanChayList);
                            recyclerView.setAdapter(AdapterCom);
                        }
                    },
                        throwable -> {
                            Log.e("loggg", "Error fetching data", throwable);
                            Toast.makeText(getApplicationContext(),"khong ket noi server", Toast.LENGTH_LONG).show();
                        }
                ));
    }

//    private void getData(int page) {
//        compositeDisposable.add(apiBanHang.getSanPham(page,loai)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        spBanChayModel -> {
//                            if (spBanChayModel != null && spBanChayModel.isSuccess() && spBanChayModel.getResult() != null) {
//                                spBanChayList = spBanChayModel.getResult();
//                                // Kiểm tra nếu danh sách rỗng
//                                if (spBanChayList != null && !spBanChayList.isEmpty()) {
//                                    ComAdapter comAdapter = new ComAdapter(getApplicationContext(), spBanChayList);
//                                    recyclerView.setAdapter(comAdapter);
//                                } else {
//                                    // Hiển thị thông báo nếu không có sản phẩm
//                                    Toast.makeText(getApplicationContext(), "Không có sản phẩm nào", Toast.LENGTH_SHORT).show();
//                                }
//                            } else {
//                                // Hiển thị thông báo nếu API không trả về thành công
//                                Toast.makeText(getApplicationContext(), "Dữ liệu không hợp lệ hoặc API không thành công", Toast.LENGTH_LONG).show();
//                            }
//                        },
//                        throwable -> {
//                            // Log chi tiết lỗi để dễ dàng xử lý hơn
//                            Log.e("API_ERROR", "Error fetching data", throwable);
//                            Toast.makeText(getApplicationContext(), "Không kết nối với server:s " + throwable.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                ));
//    }




    private void AnhXa() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview_com);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        spBanChayList = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}