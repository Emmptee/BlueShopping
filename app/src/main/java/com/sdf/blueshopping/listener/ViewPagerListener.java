package com.sdf.blueshopping.listener;

import android.support.v4.view.ViewPager;

import com.sdf.blueshopping.widget.Indicator;

/**
 * Created by shidongfang on 2017/8/16.
 */

public class ViewPagerListener implements ViewPager.OnPageChangeListener {

    private Indicator mIndicator;

    public ViewPagerListener(Indicator indicator){
        mIndicator = indicator;
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mIndicator.setmOffset(position,positionOffset);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
