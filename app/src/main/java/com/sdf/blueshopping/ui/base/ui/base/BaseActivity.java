package com.sdf.blueshopping.ui.base.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;

import com.sdf.blueshopping.R;

/**
 * Created by shidongfang on 2017/8/7.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " +this.getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: " +this.getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: " +this.getClass().getSimpleName());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent: "+this.getClass().getSimpleName());
        return super.dispatchTouchEvent(ev);
    }
    protected void openActivity(Class<?> mClass){
        Log.d(TAG, "openActivity: open " +mClass.getSimpleName());
        openActivity(mClass,null);
    }
    protected void openActivity(Class<?> mClass,Bundle bundle){
        Intent intent = new Intent(this, mClass);
        if (null != bundle){
            intent.putExtras(bundle);
        }
        Log.d(TAG, "openActivity with bundle: open  " +mClass.getSimpleName());
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    protected void openActivityWithoutAnim(Class<?> mClass,Bundle bundle){
        Intent intent = new Intent(this, mClass);
        if (null != bundle){
            intent.putExtras(bundle);
        }
        Log.d(TAG, "openActivityWithoutAnim with bundle: open  " +mClass.getSimpleName());
        startActivity(intent);
    }
    protected void openActivity(String action,Bundle bundle){
        Intent intent = new Intent(action);
        if (null != bundle){
            intent.putExtras(bundle);
        }
        Log.d(TAG, "openActivity with bundle: open  " +action);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    @Override
    public void finish() {
        super.finish();
        Log.d(TAG, "finish: " +this.getClass().getSimpleName());
        overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);

    }
}
