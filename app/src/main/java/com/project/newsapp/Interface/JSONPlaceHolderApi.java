package com.project.newsapp.Interface;

import com.project.newsapp.Model.NewsItem;
import com.project.newsapp.Model.NewsList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface JSONPlaceHolderApi {

    @GET("top-headlines")
    Call<NewsList> getNewsList(@Query("category") String category,@Query("country") String country, @Query("apiKey") String apiKey);

    @GET("everything")
    Call<NewsList> searchNews(@Query("q") String q, @Query("apiKey") String apiKey);

}
