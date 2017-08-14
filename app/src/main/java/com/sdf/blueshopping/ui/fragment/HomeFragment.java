package com.sdf.blueshopping.ui.fragment;

import android.app.usage.ConfigurationStats;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sdf.blueshopping.R;
import com.sdf.blueshopping.common.AppConstant;
import com.sdf.blueshopping.entity.HomeGridInfo;
import com.sdf.blueshopping.network.CallServer;
import com.sdf.blueshopping.network.HttpListener;
import com.sdf.blueshopping.ui.activity.CityActivity;
import com.sdf.blueshopping.ui.base.BaseFragment;
import com.sdf.blueshopping.utils.ToastUtil;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shidongfang on 2017/8/7.
 */

public class HomeFragment extends BaseFragment implements HttpListener<String> {

    private static final int GOOD_REQUEST = 0x01;
    private static final int FILM_REQUEST = 0x02;
    private static final int SCAN_QR_REQUEST = 103;
    private static final int CITY_REQUEST_CODE = 4000;

    private Handler mHandler = new Handler();
    //广告轮播
    private ViewPager bannerPager;
//    private Indicator bannerIndicator;
    private View mView;

    private List<HomeGridInfo> pageOneData = new ArrayList<>();
    private List<HomeGridInfo> pageTwoData = new ArrayList<>();
    private PullToRefreshListView mRefreshListView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView != null){
            mView = inflater.inflate(R.layout.fragment_home,null);
            initdata();
            autoScroll();
            initView(mView);
        }
        return mView;
    }
    private void initdata(){
        String[] gridTitles = getResources().getStringArray(R.array.home_bar_labels);
        TypedArray typedArray = getResources().obtainTypedArray(R.array.home_bar_icon);
        for (int i = 0; i < gridTitles.length; i++) {
            if (i < 8){
                pageOneData.add(new HomeGridInfo(typedArray.getResourceId(i,0),gridTitles[i]));
            }else {
                pageTwoData.add(new HomeGridInfo(typedArray.getResourceId(i,0),gridTitles[i]));
            }

            Request<String> goodRequest = NoHttp.createStringRequest(
                    AppConstant.RECOMMEND_URL, RequestMethod.GET);
            CallServer.getInstance().add(getActivity(),GOOD_REQUEST,goodRequest,this,true,true);

            Request<String> filmRequest = NoHttp.createStringRequest(
                    AppConstant.HOT_FILM_URL,RequestMethod.GET);
            CallServer.getInstance().add(getActivity(),FILM_REQUEST,filmRequest,this,true,true);
        }
    }
    private void autoScroll(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int currentItem = bannerPager.getCurrentItem();
                bannerPager.setCurrentItem(currentItem+1,true);
                mHandler.postDelayed(this,2000);
            }
        },2000);
    }
    private void initView(View view){
        View titleView = view.findViewById(R.id.home_titleBar);
        initTitleBar(titleView);
        mRefreshListView = (PullToRefreshListView) view.findViewById(R.id.home_pull_to_refresh_listview);

        //header
        //View bannerView = LayoutInflater.from(getActivity()).inflate(R.layout.)
    }

    private void initTitleBar(View titleView) {
        LinearLayout cityLayout = (LinearLayout) titleView.findViewById(R.id.titleBar_location_layout);
        final TextView mCityNane = (TextView) titleView.findViewById(R.id.titleBar_city_name);
        cityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CityActivity.class);
                intent.putExtra(AppConstant.KEY_CITY,mCityNane.getText().toString());
                startActivityForResult(intent,CITY_REQUEST_CODE);
            }
        });
        ImageView scanQR = (ImageView) titleView.findViewById(R.id.titleBar_scan_image);
        scanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                getActivity().startActivityForResult(intent,SCAN_QR_REQUEST);
            }
        });
        ImageView messageBox = (ImageView) titleView.findViewById(R.id.titleBar_msg_iv);
        messageBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(getActivity(),R.string.me_nologin_not_login);
            }
        });

    }

    @Override
    public void onSucceed(int what, Response<String> response) {

    }

    @Override
    public void onFailed(int what, Response<String> response) {

    }
}
