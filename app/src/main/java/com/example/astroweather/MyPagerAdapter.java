package com.example.astroweather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos) {
            case 0:
                return SunFragment.newInstance();
            case 1:
                return MoonFragment.newInstance();
            default:
                return SunFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}