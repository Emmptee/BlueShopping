package com.sdf.blueshopping.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdf.blueshopping.R;
import com.sdf.blueshopping.common.AppConstant;
import com.sdf.blueshopping.ui.base.BaseActivity;

import static android.R.attr.id;

/**
 * Created by shidongfang on 2017/8/14.
 */

public class CityActivity extends BaseActivity{

    private ImageView mTitleBarIvBack;
    private TextView mTitleBarTvTitle;
    private TextView mTitleBarTvRight;
    private FrameLayout mContentLayout;
    private TextView mErrorTV;
    private LinearLayout mErrorLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.layout_common_info_activity);
        initview();

    }

    private void initview() {
        mTitleBarIvBack = (ImageView) findViewById(R.id.common_titlebar_iv_back);
        mTitleBarTvTitle = (TextView) findViewById(R.id.common_titlebar_tv_title);
        mTitleBarTvRight = (TextView) findViewById(R.id.common_titlebar_tv_right);
        mContentLayout = (FrameLayout) findViewById(R.id.common_content_layout);
        mErrorTV = (TextView) findViewById(R.id.common_error_tv);
        mErrorLayout = (LinearLayout) findViewById(R.id.common_error_layout);

        Intent intent =getIntent();
        String cityName = intent.getStringExtra(AppConstant.KEY_CITY);

        mTitleBarTvTitle.setText(getString(R.string.current_city)+"-"+cityName);
        mTitleBarIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mErrorLayout.setVisibility(View.GONE);
    }
}
