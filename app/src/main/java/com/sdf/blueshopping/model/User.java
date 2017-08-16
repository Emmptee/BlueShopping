package com.sdf.blueshopping.model;

/**
 * Created by shidongfang on 2017/8/14.
 */

public class User {
//    private BmobFile headIcon;
    private String sex;
    private float balance;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

//    public BmobFile getHeadIcon() {
//        return headIcon;
//    }

//    public void setHeadIcon(BmobFile headIcon) {
//        this.headIcon = headIcon;
//    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
