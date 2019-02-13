package com.example.hy.recruitnew.http;

import com.example.hy.recruitnew.http.bean.RegisterData;
import com.example.hy.recruitnew.http.bean.VerificationData;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by 陈健宇 at 2019/2/13
 */
public interface AllApi {

    @GET("http://47.106.131.6/user/getCheckPicture")
    Call<VerificationData> getVerificationData();

    @POST("http://47.106.131.6/user/add")
    @FormUrlEncoded
    Call<RegisterData> getRegisterData();

}
