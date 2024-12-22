package com.example.orderfood.activity;
import android.os.Bundle;
import android.os.Message;
import android.security.identity.InvalidRequestMessageException;
import android.util.Log;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
//        loai = getIntent().getIntExtra("LOAI",2);
        AnhXa();
        ActionToolBar();
        getData();
        }

    private void getData() {
        compositeDisposable.add(apiBanHang.getSpBanChay()
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

//    private void getData() {
//        compositeDisposable.add(apiBanHang.getSanPham(page,loai)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        spBanChayModel -> {
//                            if (spBanChayModel != null && spBanChayModel.isSuccess() && spBanChayModel.getResult() != null) {
//                                spBanChayList = spBanChayModel.getResult();
//                                // Kiểm tra nếu danh sách rỗng
//                                if (spBanChayList != null && !spBanChayList.isEmpty()) {
//                                    comAdapter = new ComAdapter(getApplicationContext(), spBanChayList);
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
//                            Toast.makeText(getApplicationContext(), "Không kết nối với server: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                ));
//    }


    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview_com);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        spBanChayList = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}