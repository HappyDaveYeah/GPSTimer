package com.practicar.gpstimer.gpstimer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by HappyDave on 23/07/2014.
 */
public class MyAdapter extends FragmentPagerAdapter {

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if(position == 0) {
            fragment = new TimerFragment();
        }
        else {
            fragment = new GMapFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
