package com.project.newsapp.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.project.newsapp.Model.NewsItem;
import com.project.newsapp.Model.NewsList;

import java.util.List;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NewsItem newsItem);

    @Query("SELECT * FROM newsItem")
    LiveData<List<NewsItem>> getAllBookMarkedNews();

    @Query("DELETE FROM newsItem")
    void removeAllBookMarks();


}
