package com.sdf.blueshopping.network;


import android.content.Context;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;

/**
 * Created by shidongfang on 2017/8/10.
 */

public class CallServer {
    private RequestQueue mQueue;

    private static CallServer instance;

    private CallServer(){
        mQueue = NoHttp.newRequestQueue();
    }

    public static CallServer getInstance() {
        if (instance == null){
            synchronized (CallServer.class){
                if (instance == null){
                    instance = new CallServer();
                }
            }

        }
        return instance;
    }

    public<T> void add(Context context, int what, Request<T> request,HttpListener<T> httpListener,
                       boolean canCancel,boolean isLoading){
        mQueue.add(what,request,new HttpResponseListener<T>(context,request,httpListener,canCancel,isLoading));
    }
}
