package com.project.newsapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.project.newsapp.Adapter.NewsListAdapter;
import com.project.newsapp.Model.NewsItem;
import com.project.newsapp.Model.NewsList;
import com.project.newsapp.R;
import com.project.newsapp.viewModel.NewsTopicViewModel;

import java.sql.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BookMarkActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView news_recycler_view;
    private Button back_nav;
    private Button bookmark_btn;
    private NewsTopicViewModel newsTopicViewModel;
    private NewsListAdapter newsListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);

        news_recycler_view = findViewById(R.id.news_recycler_view);
        back_nav = findViewById(R.id.back_nav);
        back_nav.setOnClickListener(this);

        bookmark_btn = findViewById(R.id.bookmark_btn);
        bookmark_btn.setOnClickListener(this);

        newsTopicViewModel = ViewModelProviders.of(this).get(NewsTopicViewModel.class);
        newsTopicViewModel.setCategory("","",getApplication());

        LinearLayoutManager gridLayoutManager = new GridLayoutManager(BookMarkActivity.this, 2);

        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        news_recycler_view.setLayoutManager(gridLayoutManager);
        news_recycler_view.setHasFixedSize(true);

        RecyclerView.ItemAnimator animator = news_recycler_view.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        news_recycler_view.setItemViewCacheSize(20);
        news_recycler_view.setDrawingCacheEnabled(true);
        news_recycler_view.setItemAnimator(new DefaultItemAnimator());
        news_recycler_view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        newsTopicViewModel.getBookMarksList().observe(BookMarkActivity.this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(List<NewsItem> newsItems) {

                Collections.reverse(newsItems);
                newsListAdapter = new NewsListAdapter(BookMarkActivity.this,new NewsList(newsItems));
                news_recycler_view.setAdapter(newsListAdapter);

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back_nav : onBackPressed();
            break;

            case R.id.bookmark_btn : clearBookMarks();
            break;
        }
    }

    private void clearBookMarks() {
        newsTopicViewModel.clearAllBookMarks();
    }
}