package com.sdf.blueshopping.ui.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sdf.blueshopping.ui.fragment.BannerFragment;



public class BannerPagerAdapter extends FragmentPagerAdapter {

    private int[] imgRes;


    public BannerPagerAdapter(FragmentManager fm,int[] imgRes) {

        super(fm);
        this.imgRes = imgRes;

    }

    @Override
    public Fragment getItem(int position) {
        position %= imgRes.length;
        BannerFragment bannerFragment = new BannerFragment();
        bannerFragment.setImgRes(imgRes[position]);
        return bannerFragment;
    }

    @Override
    public int getCount() {
        return 10000;
    }
}
