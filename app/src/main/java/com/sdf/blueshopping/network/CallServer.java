package com.sdf.blueshopping.network;


/**
 * Created by shidongfang on 2017/8/10.
 */

public class CallServer {
    private static CallServer instance;

    private CallServer(){
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
}
