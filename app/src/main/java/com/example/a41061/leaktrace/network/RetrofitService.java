package com.example.a41061.leaktrace.network;


import com.example.a41061.leaktrace.network.response.ApiResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author FanHongyu.
 * @since 18/1/17 22:30.
 * email fanhongyu@hrsoft.net.
 */

public interface RetrofitService {


    @POST("login")
    Observable<ApiResponse> login(@Query("phone") String phone, @Query("password") String password);


    @POST("register")
    Observable<ApiResponse> register(@Query("phone") String phone, @Query("password") String password);

}
