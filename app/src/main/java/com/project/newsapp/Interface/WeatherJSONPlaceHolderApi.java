package com.project.newsapp.Interface;

import com.project.newsapp.Model.NewsList;
import com.project.newsapp.Model.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherJSONPlaceHolderApi {

    @GET("weather")
    Call<Weather> searchWeather(@Query("q") String q, @Query("appid") String apiKey);

}
