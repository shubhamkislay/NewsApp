package com.project.newsapp.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.project.newsapp.Activity.ViewNewsActivity;
import com.project.newsapp.Model.NewsItem;
import com.project.newsapp.Model.NewsList;
import com.project.newsapp.R;

import java.util.ArrayList;
import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewViewHolder>{

    private NewsList newsList;
    private Context context;
    private Activity activity;
    private static final String IMG_URL = "image";
    private static final String NEWS_TITLE = "news_title";
    private static final String NEWS_DESC = "news_desc";
    private static final String NEWS_URL = "news_url";
    private static final String AUTHOR = "author";
    private static final String PUBLISHED_AT = "published_at";
    private static final String CONTENT = "content";
    private static final String SOURCE_ID = "source_id";
    private static final String SOURCE_NAME = "source_name";

    public NewsListAdapter(Context context,NewsList newsList)
    {
        this.context = context;
        this.newsList = newsList;
        this.activity = (Activity) context;
    }
    @NonNull
    @Override
    public NewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.news_item_layout, parent, false);
        return new NewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewViewHolder holder, int position) {

        NewsItem newsItem = newsList.getNewsItems().get(holder.getAdapterPosition());
        holder.news_title.setText(newsItem.getTitle());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(activity.getDrawable(R.color.colorAccent));

        Glide.with(context)
                .load(newsItem.getUrlToImage())
                .apply(requestOptions)
                .into(holder.news_img);

        holder.news_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewNews(newsItem,holder.news_img,holder.news_title);
            }
        });


    }

    private void viewNews(NewsItem newsItem, ImageView newsImage, TextView newsTitle) {
        Intent intent = new Intent(context, ViewNewsActivity.class);

        intent.putExtra(IMG_URL,newsItem.getUrlToImage());
        intent.putExtra(NEWS_TITLE,newsItem.getTitle());
        intent.putExtra(NEWS_DESC, newsItem.getDescription());
        intent.putExtra(SOURCE_ID,newsItem.getSource().getId());
        intent.putExtra(SOURCE_NAME,newsItem.getSource().getName());
        intent.putExtra(AUTHOR, newsItem.getAuthor());
        intent.putExtra(NEWS_URL,newsItem.getUrl());
        intent.putExtra(PUBLISHED_AT,newsItem.getPublishedAt());
        intent.putExtra(CONTENT, newsItem.getContent());

        Pair[] pairs = new Pair[1];

        pairs[0] = new Pair<View,String>(newsImage,activity.getString(R.string.newsImageTransition));
       // pairs[1] = new Pair<View,String>(newsTitle,activity.getString(R.string.newsTitleTransition));

        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(activity, pairs);
        activity.startActivity(intent,activityOptions.toBundle());

    }

    @Override
    public int getItemCount() {
        return newsList.getNewsItems().size();
    }

    public static class NewViewHolder extends RecyclerView.ViewHolder {

        TextView news_title;
        ImageView news_img;
        CardView news_card;



        public NewViewHolder(@NonNull View itemView) {
            super(itemView);
            news_title = itemView.findViewById(R.id.news_title);
            news_img = itemView.findViewById(R.id.news_img);
            news_card = itemView.findViewById(R.id.news_card);
        }
    }
}
