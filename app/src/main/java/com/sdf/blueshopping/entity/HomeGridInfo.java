package com.sdf.blueshopping.entity;

import android.support.annotation.StringRes;

/**
 * Created by shidongfang on 2017/8/16.
 */

public class HomeGridInfo {
    private @StringRes int gridIcon;
    private String gridTitle;

    public HomeGridInfo(int gridIcon,String gridTitle){
        this.gridIcon = gridIcon;
        this.gridTitle = gridTitle;
    }

    public int getGridIcon() {
        return gridIcon;
    }

    public String getGridTitle() {
        return gridTitle;
    }

    public void setGridIcon(int gridIcon) {
        this.gridIcon = gridIcon;
    }

    public void setGridTitle(String gridTitle) {
        this.gridTitle = gridTitle;
    }
}
