package com.project.newsapp.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.View;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.view.GravityCompat;

import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.project.newsapp.Adapter.NewsPagerAdapter;
import com.project.newsapp.Model.Weather;
import com.project.newsapp.R;
import com.project.newsapp.viewModel.NewsTopicViewModel;
import com.project.newsapp.viewModel.WeatherViewModel;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
        //implements NavigationView.OnNavigationItemSelectedListener {

    ImageButton nav_btn;
    MotionLayout parent_motion_layout;
    private ImageButton search_btn;
    private TextView bookMarks;
    private TextView city;
    private TextView temp;
    private static final String CITY = "Bangalore";
    private WeatherViewModel weatherViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        parent_motion_layout = findViewById(R.id.parent_motion_layout);
        nav_btn = findViewById(R.id.nav_btn);
        nav_btn.setOnClickListener(this);
        setSupportActionBar(toolbar);
        search_btn = findViewById(R.id.search_btn);
        search_btn.setOnClickListener(this);

        bookMarks = findViewById(R.id.bookMarks);


        bookMarks.setOnClickListener(this);

        city = findViewById(R.id.city);
        temp = findViewById(R.id.temp);

        toolbar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        NewsPagerAdapter newsPagerAdapter = new NewsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(newsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        getWindow().setEnterTransition(null);

        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        weatherViewModel.setCity(CITY,getApplication());
        city.setText(CITY);

        weatherViewModel.getWeather().observe(MainActivity.this, new Observer<Weather>() {
            @Override
            public void onChanged(Weather weather) {


                String temperature = String.valueOf(weather.getMain().getTemp() - 273.15).substring(0,4);

                temp.setText(""+temperature+" \u2103");
            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.nav_btn: parent_motion_layout.transitionToEnd();
            break;

            case R.id.search_btn:
                startActivity(new Intent(MainActivity.this,SearchNewsActivity.class));
            break;

            case R.id.bookMarks:
                startActivity(new Intent(MainActivity.this,BookMarkActivity.class));
                break;
        }

    }




}
