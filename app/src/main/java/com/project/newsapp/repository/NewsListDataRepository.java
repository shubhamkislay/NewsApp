package com.project.newsapp.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.project.newsapp.Database.NewsDatabase;
import com.project.newsapp.Interface.JSONPlaceHolderApi;
import com.project.newsapp.Model.NewsItem;
import com.project.newsapp.Model.NewsList;
import com.project.newsapp.Model.NewsListLiveData;
import com.project.newsapp.Model.NewsSource;
import com.project.newsapp.R;
import com.project.newsapp.viewModel.NewsTopicViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsListDataRepository {

    private String category;
    private static int TOO_MANY_CALLS = 429;
    private NewsLiveData newsListLiveData;
    private static  String API_KEY = "1176cac9454e4fc98115a92a9401d923";
    private static  String BASE_URL = "https://newsapi.org/v2/";
    private static final String LOG_TAG = "LIVE DATA";
    private static final String COUNTRY = "in";
    private static String mDataType = "category";
    private static final String SEARCH = "search";
    private Application mApplication;
    private NewsDatabase newsDatabase;
    private LiveData<List<NewsItem>> newsBookBarks;

    public NewsListDataRepository(String category,String dataType, Application application)
    {
        this.category = category;
        mDataType = dataType;
        mApplication = application;

        API_KEY = application.getString(R.string.api_key);
        BASE_URL = application.getString(R.string.base_url);

        newsDatabase = NewsDatabase.getINSTANCE(application);
        newsBookBarks = newsDatabase.newsDao().getAllBookMarkedNews();

        newsListLiveData = new NewsLiveData(category,mDataType);

    }


    public LiveData<NewsListLiveData> getNewsList() {
        return newsListLiveData;
    }

    public void searchNews(String news)
    {
        newsListLiveData.searchNews(news);
    }

    public static class NewsLiveData extends LiveData<NewsListLiveData> {

        private String category;
        private String dataType;
        public NewsLiveData(String category,String dataType) {
            this.category = category;
            this.dataType = dataType;
        }

        @Override
        protected void onActive() {

            if(dataType.equals(SEARCH)) {
                if (category.length() > 0)
                    searchNews(category);
            }
            else
                getList(category);
        }
        @Override
        protected void onInactive() {}

        private void getList(String category)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();

            JSONPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JSONPlaceHolderApi.class);

            Call<NewsList> call = jsonPlaceHolderApi.getNewsList(category,COUNTRY,API_KEY);

            call.enqueue(new Callback<NewsList>() {
                @Override
                public void onResponse(Call<NewsList> call, Response<NewsList> response) {
                    if(!response.isSuccessful())
                    {
                        /*if(response.code() == TOO_MANY_CALLS)
                            setValue("Too many calls were made using this the current api.\nThis is a free api, and can make only 100 requests in a day");
                        else
                            setValue("Error retrieving data.\nFind out the reason using the error code: "+response.code() );
                        Log.d("LIVE DATA",response.toString());*/
                        Log.d(LOG_TAG,response.toString());
                        NewsItem newsItem = new NewsItem();
                        newsItem.setAuthor("snagarajan@businessinsider.com (Shalini Nagarajan)");
                        newsItem.setContent("Astrid Stawiarz/Getty Images\r\nBig Short money manager Michael Burry ditched about 50% of his bullish options positions in Alphabet and Facebook in the third quarter, according to a 13F filed with t… [+1938 chars]");
                        newsItem.setDescription("Burry's largest holding is still Alphabet, but he ditched 50% of his call position in the Google parent, according to a 13F filing.");
                        newsItem.setPublishedAt("2020-11-20T09:20:19Z");
                        newsItem.setUrl("https://www.businessinsider.com/big-short-investor-michael-burry-ditched-stocks-13f-q3-2020-11");
                        newsItem.setUrlToImage("https://images2.markets.businessinsider.com/5fb778b450e71a0011556642?format=jpeg");
                        newsItem.setTitle("Big Short' investor Michael Burry, the man who predicted the 2008 housing collapse, dumped these 5 stocks from his portfolio in the third quarter");
                        newsItem.setSource(new NewsSource("business-insider","Business Insider"));
                        List<NewsItem> newsLists = new ArrayList<>();
                        /*for(int i = 0;i<10;i++)
                            newsLists.add(newsItem);*/


                        NewsList newsList = new NewsList();
                        newsList.setNewsItems(newsLists);

                        NewsListLiveData newsListLiveData = new NewsListLiveData();
                        newsListLiveData.setNewsList(newsList);
                        newsListLiveData.setStatusCode(response.code());
                        setValue(newsListLiveData);
                    }
                    else
                    {
                        NewsList newsItems = response.body();
                        NewsListLiveData newsListLiveData = new NewsListLiveData();
                        newsListLiveData.setNewsList(newsItems);
                        newsListLiveData.setStatusCode(response.code());
                        setValue(newsListLiveData);
                    }
                }

                @Override
                public void onFailure(Call<NewsList> call, Throwable t) {
                    Log.d(LOG_TAG,t.getMessage());
                }
            });


        }

        private void searchNews(String key)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();

            JSONPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JSONPlaceHolderApi.class);

            Call<NewsList> call = jsonPlaceHolderApi.searchNews(key,API_KEY);

            call.enqueue(new Callback<NewsList>() {
                @Override
                public void onResponse(Call<NewsList> call, Response<NewsList> response) {
                    if(!response.isSuccessful())
                    {
                        List<NewsItem> newsItemList = new ArrayList<>();
                        NewsList newsList = new NewsList();
                        newsList.setNewsItems(newsItemList);
                        NewsListLiveData newsListLiveData = new NewsListLiveData();
                        newsListLiveData.setNewsList(newsList);
                        newsListLiveData.setStatusCode(response.code());
                        setValue(newsListLiveData);
                    }
                    else
                    {
                        NewsList newsItems = response.body();
                        NewsListLiveData newsListLiveData = new NewsListLiveData();
                        newsListLiveData.setNewsList(newsItems);
                        newsListLiveData.setStatusCode(response.code());
                        setValue(newsListLiveData);
                    }
                }

                @Override
                public void onFailure(Call<NewsList> call, Throwable t) {
                    Log.d(LOG_TAG,t.getMessage());
                }
            });


        }

    }

    public void insertBookMark(NewsItem newsItem)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                newsDatabase.newsDao().insert(newsItem);
            }
        }).start();
    }

    public void deleteAllBookmarks()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                newsDatabase.newsDao().removeAllBookMarks();
            }
        }).start();
    }

    public  LiveData<List<NewsItem>> getAllBookMarks(){return newsBookBarks;}
}
