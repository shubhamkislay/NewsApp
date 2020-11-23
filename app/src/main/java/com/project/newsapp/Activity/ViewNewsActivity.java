package com.project.newsapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.project.newsapp.Adapter.SearchNewsListAdapter;
import com.project.newsapp.Model.NewsItem;
import com.project.newsapp.Model.NewsListLiveData;
import com.project.newsapp.Model.NewsSource;
import com.project.newsapp.R;
import com.project.newsapp.viewModel.NewsTopicViewModel;

public class ViewNewsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView news_img;
    private TextView news_title;
    private TextView news_desc;
    private Intent intent;
    private String imageUrl;
    private String newsTitle;
    private String newsDesc;
    private String newsUrl;
    private String author;
    private String publishedAt;
    private String content;
    private String sourceId;
    private String sourceName;

    private static final String IMG_URL = "image";
    private static final String NEWS_TITLE = "news_title";
    private static final String NEWS_DESC = "news_desc";
    private static final String NEWS_URL = "news_url";
    private static final String AUTHOR = "author";
    private static final String PUBLISHED_AT = "published_at";
    private static final String CONTENT = "content";
    private static final String SOURCE_ID = "source_id";
    private static final String SOURCE_NAME = "source_name";

    private NewsTopicViewModel newsTopicViewModel;
    private Button back_nav;
    private Button bookmark_btn;
    private TextView news_source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news);

        news_img = findViewById(R.id.news_img);
        news_title = findViewById(R.id.news_title);
        news_desc = findViewById(R.id.news_desc);
        back_nav = findViewById(R.id.back_nav);
        bookmark_btn = findViewById(R.id.bookmark_btn);
        news_source = findViewById(R.id.news_source);

        intent = getIntent();

        getData();
        getWindow().setExitTransition(null);

        newsTopicViewModel = ViewModelProviders.of(this).get(NewsTopicViewModel.class);
        newsTopicViewModel.setCategory("","",getApplication());

        back_nav.setOnClickListener(this);
        bookmark_btn.setOnClickListener(this);

    }

    private void getData() {
        imageUrl = intent.getStringExtra(IMG_URL);
        newsTitle = intent.getStringExtra(NEWS_TITLE);
        newsDesc = intent.getStringExtra(NEWS_DESC);
        sourceId = intent.getStringExtra(SOURCE_ID);
        sourceName = intent.getStringExtra(SOURCE_NAME);
        author = intent.getStringExtra(AUTHOR);
        newsUrl = intent.getStringExtra(NEWS_URL);
        publishedAt = intent.getStringExtra(PUBLISHED_AT);
        content = intent.getStringExtra(CONTENT);

        populateViews();
    }

    private void populateViews() {
        news_title.setText(newsTitle);
        news_desc.setText(newsDesc);
        news_source.setText("By "+sourceName);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(getDrawable(R.color.colorAccent));

        Glide.with(this).load(imageUrl).apply(requestOptions).into(news_img);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch(id)
        {
            case R.id.bookmark_btn: addToBookMark();
            break;

            case R.id.back_nav: onBackPressed();
            break;

        }
    }

    private void addToBookMark() {
        NewsItem newsItem = new NewsItem();

        newsItem.setSource(new NewsSource(sourceId,sourceName));
        newsItem.setAuthor(author);
        newsItem.setTitle(newsTitle);
        newsItem.setDescription(newsDesc);
        newsItem.setUrl(newsUrl);
        newsItem.setUrlToImage(imageUrl);
        newsItem.setPublishedAt(publishedAt);
        newsItem.setContent(content);

        newsTopicViewModel.insertBookMark(newsItem);

        Toast.makeText(this,"Added to bookmarks",Toast.LENGTH_SHORT).show();
        bookmark_btn.setBackground(getDrawable(R.drawable.ic_baseline_bookmark_24));
    }
}