package com.project.newsapp.viewModel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.project.newsapp.Model.NewsItem;
import com.project.newsapp.Model.NewsListLiveData;
import com.project.newsapp.repository.NewsListDataRepository;

import java.util.List;

public class NewsTopicViewModel extends ViewModel {

    private String category;
    private NewsListDataRepository newsListDataRepository;
    private static String mDataType = "category";

    public void setCategory(String category,String datatype,Application application) {
        this.category = category;
        mDataType = datatype;
        newsListDataRepository = new NewsListDataRepository(category,mDataType,application);
    }
    public LiveData<NewsListLiveData> getNewsList() {
        return newsListDataRepository.getNewsList();
    }

    public void searchNews(String news)
    {
        newsListDataRepository.searchNews(news);
    }

    public void insertBookMark(NewsItem newsItem) { newsListDataRepository.insertBookMark(newsItem); }

    public LiveData<List<NewsItem>> getBookMarksList() {return newsListDataRepository.getAllBookMarks();}

    public void clearAllBookMarks() { newsListDataRepository.deleteAllBookmarks(); }
}