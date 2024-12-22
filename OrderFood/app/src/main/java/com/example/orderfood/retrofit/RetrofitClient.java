package com.example.orderfood.retrofit;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClient {
    private static Retrofit instance;
    public static Retrofit getInstance(String baseUrl)
    {
        if(instance == null)
        {
            instance = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create()) // Nếu sử dụng JSON
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // RxJava3 Adapter
                    .build();

        }
        return instance;
    }
}



