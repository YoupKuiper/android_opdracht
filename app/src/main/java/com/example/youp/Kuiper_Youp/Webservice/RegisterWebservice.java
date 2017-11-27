package com.example.youp.Kuiper_Youp.Webservice;

import com.example.youp.Kuiper_Youp.Models.RegisterResponse;
import com.example.youp.Kuiper_Youp.Models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by youp on 10/6/2017.
 */

public interface RegisterWebservice {
    @POST("Users/register")
    Call<RegisterResponse> register(@Body User user);
}
