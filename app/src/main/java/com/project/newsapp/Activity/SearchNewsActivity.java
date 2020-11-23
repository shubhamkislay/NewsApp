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
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.newsapp.Adapter.NewsListAdapter;
import com.project.newsapp.Adapter.SearchNewsListAdapter;
import com.project.newsapp.Model.NewsListLiveData;
import com.project.newsapp.R;
import com.project.newsapp.viewModel.NewsTopicViewModel;

public class SearchNewsActivity extends AppCompatActivity implements TextView.OnEditorActionListener {


    private NewsTopicViewModel newsTopicViewModel;
    private EditText search_news;
    private static final String SEARCH = "search";
    private static int TOO_MANY_CALLS = 429;
    private TextView result_text;
    private SearchNewsListAdapter searchNewsListAdapter;
    private RecyclerView search_news_recyclerView;
    private boolean stopRefresh = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_news);

        search_news = findViewById(R.id.search_news);
        search_news.setOnEditorActionListener(this);
        result_text = findViewById(R.id.result_text);

        search_news_recyclerView  = findViewById(R.id.search_news_recyclerView);


        newsTopicViewModel = ViewModelProviders.of(this).get(NewsTopicViewModel.class);

        newsTopicViewModel.setCategory("",SEARCH,getApplication());

        LinearLayoutManager gridLayoutManager = new GridLayoutManager(SearchNewsActivity.this, 1);

        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        search_news_recyclerView.setLayoutManager(gridLayoutManager);
        search_news_recyclerView.setHasFixedSize(true);

        RecyclerView.ItemAnimator animator = search_news_recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }


        search_news_recyclerView.setItemViewCacheSize(20);
        search_news_recyclerView.setDrawingCacheEnabled(true);
        search_news_recyclerView.setItemAnimator(new DefaultItemAnimator());
        search_news_recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        newsTopicViewModel.getNewsList().observe(this, new Observer<NewsListLiveData>() {
            @Override
            public void onChanged(NewsListLiveData newsListLiveData) {
                if(!stopRefresh) {
                    newsListLiveData.getNewsList();
                    if (newsListLiveData.getStatusCode() == TOO_MANY_CALLS)
                        result_text.setText(getString(R.string.tooManyMsg));

                    //Toast.makeText(SearchNewsActivity.this, "Size " + newsListLiveData.getNewsList().getNewsItems().size(), Toast.LENGTH_SHORT).show();

                    searchNewsListAdapter = new SearchNewsListAdapter(SearchNewsActivity.this, newsListLiveData.getNewsList());
                    search_news_recyclerView.setAdapter(searchNewsListAdapter);
                }
                stopRefresh = false;

            }
        });



    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_SEARCH) {
            String category = search_news.getText().toString();
            newsTopicViewModel.searchNews(category);
            //Toast.makeText(SearchNewsActivity.this,"Searching for "+category,Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopRefresh = true;

    }
}