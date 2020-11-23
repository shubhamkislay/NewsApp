package com.project.newsapp.Adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.project.newsapp.Fragment.PlaceholderFragment;
import com.project.newsapp.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class NewsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{
            R.string.business_category,
            R.string.sports_category,
            R.string.entertainment_category,
            R.string.technology_category,
            R.string.science_category,
            R.string.health_catgory,
            R.string.general_category};

    private final Context mContext;

    public NewsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        int currentPos = position;
        return PlaceholderFragment.newInstance(position + 1,
                mContext.getResources().getString(TAB_TITLES[currentPos]));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 6 total pages.
        return TAB_TITLES.length;
    }
}