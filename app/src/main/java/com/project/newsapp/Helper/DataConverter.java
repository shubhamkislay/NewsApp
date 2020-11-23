package com.project.newsapp.Helper;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.newsapp.Model.NewsSource;

import java.lang.reflect.Type;

public class DataConverter {

    @TypeConverter
    public String fromNewsSource(NewsSource newsSource) {
        if (newsSource == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<NewsSource>() {}.getType();
        String json = gson.toJson(newsSource, type);
        return json;
    }

    @TypeConverter
    public NewsSource toNewsSource(String newsSouceString) {
        if (newsSouceString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<NewsSource>() {}.getType();
        NewsSource countryLangList = gson.fromJson(newsSouceString, type);
        return countryLangList;
    }
}