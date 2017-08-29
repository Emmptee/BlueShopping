package com.sdf.blueshopping.entity;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.uuzuche.lib_zxing.ZApplication;
import com.yolanda.nohttp.NoHttp;

/**
 * Created by shidongfang on 2017/8/16.
 */

public class MyApplication extends ZApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //NoHttp初始化
        NoHttp.initialize(this);
        //Fresco初始化
        Fresco.initialize(this);
    }
}
