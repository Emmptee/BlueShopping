package com.sdf.blueshopping.network;


import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by asus on 2016/8/30.
 */
public interface HttpListener<T> {
    public void onSucceed(int what, Response<T> response);
    public void onFailed(int what, Response<T> response);
}
