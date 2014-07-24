package com.practicar.gpstimer.gpstimer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by HappyDave on 23/07/2014.
 */
public class MyAdapter extends FragmentStatePagerAdapter {

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if(position == 0) {
            fragment = new Timer();
        }
        else {
            fragment = new GMap();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}