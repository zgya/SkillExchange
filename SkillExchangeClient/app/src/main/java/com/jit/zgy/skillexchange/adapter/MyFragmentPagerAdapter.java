package com.jit.zgy.skillexchange.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.jit.zgy.skillexchange.activity.MainActivity;
import com.jit.zgy.skillexchange.fragment.DarenFragment;
import com.jit.zgy.skillexchange.fragment.MessageFragment;
import com.jit.zgy.skillexchange.fragment.MineFragment;
import com.jit.zgy.skillexchange.fragment.ShouyeFragment;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT = 4;
    private ShouyeFragment shouyeFragment = null;
    private DarenFragment darenFragment = null;
    private MessageFragment messageFragment = null;
    private MineFragment mineFragment = null;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        shouyeFragment = new ShouyeFragment();
        darenFragment = new DarenFragment();
        messageFragment = new MessageFragment();
        mineFragment = new MineFragment();
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_ONE:
                fragment = shouyeFragment;
                break;
            case MainActivity.PAGE_TWO:
                fragment = darenFragment;
                break;
            case MainActivity.PAGE_THREE:
                fragment = messageFragment;
                break;
            case MainActivity.PAGE_FOUR:
                fragment = mineFragment;
                break;
        }
        return fragment;
    }
}
