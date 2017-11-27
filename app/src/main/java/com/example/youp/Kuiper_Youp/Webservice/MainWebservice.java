package com.example.youp.Kuiper_Youp.Webservice;

import com.example.youp.Kuiper_Youp.Models.RootObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MainWebservice {
    @GET("Articles")
    Call<RootObject> getArticles();

    @GET("Articles/{id}")
    Call<RootObject> getMoreArticles(@Path("id") int id, @Query("count") int count);

    @GET("Articles")
    Call<RootObject> getArticlesForLoggedInUser(@Header("x-authtoken") String token);

    @GET("Articles/{id}")
    Call<RootObject> getMoreArticlesForLoggedInUser(@Header("x-authtoken") String token, @Path("id") int id, @Query("count") int count);

    @GET("Articles/liked")
    Call<RootObject> getAllLikedArticles(@Header("x-authtoken") String token);

    @PUT("Articles/{id}/like")
    Call<ResponseBody> likeArticle(@Header("x-authtoken") String token, @Path("id") int id);

    @DELETE("Articles/{id}/like")
    Call<ResponseBody> noLongerLikeArticle(@Header("x-authtoken") String token, @Path("id") int id);


}
