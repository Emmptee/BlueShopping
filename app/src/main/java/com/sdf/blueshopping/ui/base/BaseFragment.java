package com.sdf.blueshopping.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.sdf.blueshopping.R;

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
        Intent intent = new Intent(getActivity(), mClass);
        if (null !=bundle){
            intent.putExtras(bundle);
        }
        Log.d(TAG, "openActivity with bundle: open " +mClass.getSimpleName());
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
    protected void openActivity(String action,Bundle bundle){
        Intent intent = new Intent(action);
        if (null != bundle){
            intent.putExtras(bundle);
        }
        Log.d(TAG, "openActivity by action : action is ==== " + action);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
}
