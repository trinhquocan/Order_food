package com.example.orderfood.retrofit;

import io.reactivex.rxjava3.core.Observable;  // Import đúng Observable của RxJava 3
import com.example.orderfood.model.LoaiSpModel;
import com.example.orderfood.model.SpBanChay;
import com.example.orderfood.model.SpBanChayModel;

import retrofit2.http.GET;

public interface ApiBanHang {
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();

    @GET("getspbanchay.php")
    Observable<SpBanChayModel> getSpBanChay();
}
