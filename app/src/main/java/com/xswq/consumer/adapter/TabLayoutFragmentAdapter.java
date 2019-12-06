package com.xswq.consumer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class TabLayoutFragmentAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mListFragment;
    private String [] mTitles;

    public TabLayoutFragmentAdapter(FragmentManager fm, String[] titles, List<Fragment> listFragment) {
        super(fm);
        mTitles = titles;
        mListFragment = listFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return mListFragment.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }


}
