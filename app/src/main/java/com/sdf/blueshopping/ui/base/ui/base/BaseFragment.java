package com.sdf.blueshopping.ui.base.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by shidongfang on 2017/8/7.
 */

public class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    protected void openActivity(Class<?> mClass){
        Log.d(TAG, "openActivity: " +mClass.getSimpleName());
        openActivity(mClass,null);
    }
    protected void openActivity(Class<?> mClass, Bundle bundle){
        new Intent(getActivity(),mClass);
    }
}
