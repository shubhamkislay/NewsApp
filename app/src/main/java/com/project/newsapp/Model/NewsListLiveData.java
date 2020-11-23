package com.project.newsapp.Model;

public class NewsListLiveData {
    private NewsList newsList;
    private int statusCode;

    public NewsListLiveData() {
    }

    public NewsList getNewsList() {
        return newsList;
    }

    public void setNewsList(NewsList newsList) {
        this.newsList = newsList;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
