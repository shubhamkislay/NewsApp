package com.project.newsapp.Model;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "newsList")
public class NewsList {

    @SerializedName("articles")
    private List<NewsItem> newsItems;

    public List<NewsItem> getNewsItems() {
        return newsItems;
    }

    public void setNewsItems(List<NewsItem> newsItems) {
        this.newsItems = newsItems;
    }

    public NewsList(List<NewsItem> newsItems) {
        this.newsItems = newsItems;
    }

    public NewsList() {
    }
}
