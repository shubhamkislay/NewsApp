package com.project.newsapp.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.project.newsapp.Database.NewsDatabase;
import com.project.newsapp.Interface.JSONPlaceHolderApi;
import com.project.newsapp.Interface.WeatherJSONPlaceHolderApi;
import com.project.newsapp.Model.NewsItem;
import com.project.newsapp.Model.NewsList;
import com.project.newsapp.Model.NewsListLiveData;
import com.project.newsapp.Model.NewsSource;
import com.project.newsapp.Model.Weather;
import com.project.newsapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherDataRespository {

    private String mCity;
    private WeatherLiveData weatherLiveData;
    private static  String API_KEY = "c6194d2af2f5ce11ad34ebb9a3bc0e5e";
    private static  String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static final String LOG_TAG = "WEATHER DATA";
    private Application mApplication;
    private NewsDatabase newsDatabase;
    private LiveData<List<NewsItem>> newsBookBarks;

    public WeatherDataRespository(String city, Application application)
    {
        this.mCity = city;
        mApplication = application;
        API_KEY = application.getString(R.string.weather_api_key);
        BASE_URL = application.getString(R.string.weather_base_url);

        weatherLiveData = new WeatherLiveData(city);

    }


    public LiveData<Weather> getWeather() {
        return weatherLiveData;
    }


    public static class WeatherLiveData extends LiveData<Weather> {

        private String city;

        public WeatherLiveData(String city) {
            this.city = city;
        }

        @Override
        protected void onActive() {

                getWeather(city);
        }
        @Override
        protected void onInactive() {}

        private void getWeather(String city)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();

            WeatherJSONPlaceHolderApi weatherJSONPlaceHolderApi = retrofit.create(WeatherJSONPlaceHolderApi.class);

            Call<Weather> call = weatherJSONPlaceHolderApi.searchWeather(city,API_KEY);

            call.enqueue(new Callback<Weather>() {
                @Override
                public void onResponse(Call<Weather> call, Response<Weather> response) {
                    if(response.isSuccessful())
                    {
                        Weather weather = response.body();
                        setValue(weather);
                    }
                    else
                    {
                        Log.d("Weather Data",response.toString());
                    }
                }

                @Override
                public void onFailure(Call<Weather> call, Throwable t) {
                    Log.d(LOG_TAG,t.getMessage());
                }
            });


        }


    }


}
