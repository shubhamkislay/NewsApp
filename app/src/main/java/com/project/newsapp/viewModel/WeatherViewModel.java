package com.project.newsapp.viewModel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.project.newsapp.Model.NewsItem;
import com.project.newsapp.Model.NewsListLiveData;
import com.project.newsapp.Model.Weather;
import com.project.newsapp.repository.NewsListDataRepository;
import com.project.newsapp.repository.WeatherDataRespository;

import java.util.List;

public class WeatherViewModel extends ViewModel {

    private WeatherDataRespository weatherDataRespository;

    public void setCity(String city,Application application) {
        weatherDataRespository = new WeatherDataRespository(city,application);
    }
    public LiveData<Weather> getWeather() {
        return weatherDataRespository.getWeather();
    }

}