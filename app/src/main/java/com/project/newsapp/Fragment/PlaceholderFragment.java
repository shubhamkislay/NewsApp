package com.project.newsapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.project.newsapp.Adapter.NewsListAdapter;
import com.project.newsapp.Model.NewsListLiveData;
import com.project.newsapp.R;
import com.project.newsapp.viewModel.NewsTopicViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment  {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String CATEGORY = "business";
    private static String category;
    private static int TOO_MANY_CALLS = 429;
    NewsListAdapter newsListAdapter;
    View root;


    private NewsTopicViewModel newsTopicViewModel;
    private boolean stopRefresh = false;

    public static PlaceholderFragment newInstance(int index, String mCategory) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putString(CATEGORY,mCategory);
        fragment.setArguments(bundle);

        category = mCategory;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsTopicViewModel = ViewModelProviders.of(this).get(NewsTopicViewModel.class);
        if (getArguments() != null) {

            category = getArguments().getString(CATEGORY);
        }
        newsTopicViewModel.setCategory(category,category,getActivity().getApplication());
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {


        root = inflater.inflate(R.layout.fragment_tabbed, container, false);
        RecyclerView news_recycler_view = root.findViewById(R.id.news_recycler_view);


        final TextView responseCodeTextView = root.findViewById(R.id.section_label);


        LinearLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

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

        

        
        newsTopicViewModel.getNewsList().observe(getViewLifecycleOwner(), new Observer<NewsListLiveData>() {
            @Override
            public void onChanged(NewsListLiveData newsListLiveData) {


                if(!stopRefresh) {

                    newsListLiveData.getNewsList();
                    try {
                        if (newsListLiveData.getStatusCode() == TOO_MANY_CALLS)
                            responseCodeTextView.setText(getActivity().getString(R.string.tooManyMsg));
                    } catch (Exception e) {
                        //handle if getActivity() is null
                    }


                    newsListAdapter = new NewsListAdapter(getActivity(), newsListLiveData.getNewsList());
                    news_recycler_view.setAdapter(newsListAdapter);
                }
                stopRefresh = false;

            }
        });
        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        stopRefresh = true;
    }


}