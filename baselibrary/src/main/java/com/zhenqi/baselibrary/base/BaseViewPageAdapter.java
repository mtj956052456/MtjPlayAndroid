package com.zhenqi.baselibrary.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class BaseViewPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> mList;
    private String[] titleList;

    public BaseViewPageAdapter(FragmentManager fm, List<Fragment> list, String... titleList) {
        super(fm);
        this.mList = list;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titleList != null) {
            return titleList[position];
        }
        return super.getPageTitle(position);
    }
}