package com.example.qiuchenly.comicparse.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class BaseFragmentPagerStatement extends FragmentStatePagerAdapter {

    private List<Fragment> _fragment;

    public BaseFragmentPagerStatement(FragmentManager fm, List<Fragment> _fragment) {
        super(fm);
        this._fragment = _fragment;
    }

    @Override
    public Fragment getItem(int position) {
        return _fragment.get(position);
    }

    @Override
    public int getCount() {
        return _fragment.size();
    }
}
