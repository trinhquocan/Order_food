package com.example.orderfood.retrofit;

import io.reactivex.rxjava3.core.Observable;  // Import đúng Observable của RxJava 3
import com.example.orderfood.model.LoaiSpModel;
import com.example.orderfood.model.SpBanChay;
import com.example.orderfood.model.SpBanChayModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanHang {
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();

    @GET("getspbanchay.php")
    Observable<SpBanChayModel> getSpBanChay();

    @POST("getspbanchay1.php")
    Observable<SpBanChayModel> getSpBanChay1();

    @POST("getspbanchay2.php")
    Observable<SpBanChayModel> getSpBanChay2();

//    @POST("getspbanchay3.php")
//    Observable<SpBanChayModel> getSpBanChay3();

//    @POST("chitiet.php")
//    @FormUrlEncoded
//    Observable<SpBanChayModel> getSanPham(
//            @Field("page") int page,
//            @Field("LOAI") int loai
//    );
}
