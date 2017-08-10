package com.sdf.blueshopping.ui.fragment;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdf.blueshopping.R;
import com.sdf.blueshopping.common.AppConstant;
import com.sdf.blueshopping.entity.HomeGridInfo;
import com.sdf.blueshopping.ui.base.BaseFragment;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shidongfang on 2017/8/7.
 */

public class HomeFragment extends BaseFragment {

    private View mView;

    private List<HomeGridInfo> pageOneData = new ArrayList<>();
    private List<HomeGridInfo> pageTwoData = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView != null){
            mView = inflater.inflate(R.layout.fragment_home,null);

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

        }
    }
}
