package com.sdf.blueshopping.ui.fragment;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sdf.blueshopping.R;
import com.sdf.blueshopping.common.AppConstant;
import com.sdf.blueshopping.entity.FilmInfo;
import com.sdf.blueshopping.entity.GoodsInfo;
import com.sdf.blueshopping.entity.HomeGridInfo;
import com.sdf.blueshopping.listener.ViewPagerListener;
import com.sdf.blueshopping.model.User;
import com.sdf.blueshopping.network.CallServer;
import com.sdf.blueshopping.network.HttpListener;
import com.sdf.blueshopping.ui.Adapter.BannerPagerAdapter;
import com.sdf.blueshopping.ui.Adapter.GoodsListAdapter;
import com.sdf.blueshopping.ui.Adapter.GridAdapter;
import com.sdf.blueshopping.ui.Adapter.ViewPagerAdapter;
import com.sdf.blueshopping.ui.activity.CityActivity;
import com.sdf.blueshopping.ui.activity.DetailActivity;
import com.sdf.blueshopping.ui.base.BaseFragment;
import com.sdf.blueshopping.utils.ToastUtil;
import com.sdf.blueshopping.widget.Indicator;
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

    public static final String GOODS_ID = "goodsId";
    public static final String GOODS_SEVEN_REFUND = "sevenRefund";
    public static final String GOODS_TIME_REFUND = "timeRefund";
    public static final String GOODS_BOUGHT = "bought";

    private int [] imgRes = new int[]{R.drawable.banner01,R.drawable.banner02,R.drawable.banner03};
    private Handler mHandler = new Handler();
    //广告轮播
    private ViewPager bannerPager;
    private Indicator bannerIndicator;
    private View mView;

    private List<HomeGridInfo> pageOneData = new ArrayList<>();
    private List<HomeGridInfo> pageTwoData = new ArrayList<>();
    private PullToRefreshListView mRefreshListView;
    private List<View> mViewList = new ArrayList<>();


    private List<GoodsInfo.ResultBean.GoodlistBean> mGoodlist = new ArrayList<>();
    private List<FilmInfo.ResultBean> mFilmList = new ArrayList<>();
    private LinearLayout mFilmLayout;
    private ListView mListView;
    private GoodsListAdapter mGoodsListAdapter;

    //是否正在刷新
    private boolean isRefreshing = false;
    private TextView mCityName;


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
        View headview = LayoutInflater.from(getActivity()).inflate(R.layout.home_head_page,null);
        //banner
        View bannerView = LayoutInflater.from(getActivity()).inflate(R.layout.home_head_page,null);
        bannerPager = (ViewPager) bannerView.findViewById(R.id.home_head_include_banner);
        bannerIndicator = (Indicator) bannerView.findViewById(R.id.home_banner_indicator);
        bannerPager.setAdapter(new BannerPagerAdapter(getChildFragmentManager(),imgRes));
        bannerPager.addOnPageChangeListener(new ViewPagerListener(bannerIndicator));
        ViewPager headPager = (ViewPager) headview.findViewById(R.id.home_header_pager);
        Indicator headIndicator = (Indicator) headview.findViewById(R.id.home_head_indicator);

        //第一页
        View pageOne = LayoutInflater.from(getActivity()).inflate(R.layout.home_gridview,null);
        GridView gridViewOne = (GridView) pageOne.findViewById(R.id.home_gridView);
        gridViewOne.setAdapter(new GridAdapter(getActivity(),pageOneData));
        gridViewOne.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        //第二页
        View pageTwo = LayoutInflater.from(getActivity()).inflate(R.layout.home_gridview,null);
        GridView gridViewTwo = (GridView) pageTwo.findViewById(R.id.home_gridView);
        gridViewTwo.setAdapter(new GridAdapter(getActivity(),pageTwoData));
        gridViewTwo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        mViewList.add(pageOne);
        mViewList.add(pageTwo);
        headPager.setAdapter(new ViewPagerAdapter(mViewList));
        //电影
        View filmView = headview.findViewById(R.id.home_head_include_film);
        mFilmLayout = (LinearLayout) filmView.findViewById(R.id.home_film_ll);
        
        mListView = mRefreshListView.getRefreshableView();
        mListView.addHeaderView(headview);
        mListView.setHeaderDividersEnabled(false);
        int headerViewCount = mListView.getHeaderViewsCount();
        mGoodsListAdapter = new GoodsListAdapter(getActivity(),mGoodlist,headerViewCount);
        mRefreshListView.setAdapter(mGoodsListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString(GOODS_ID,mGoodlist.get(position-2).getGoods_id());
                bundle.putString(GOODS_SEVEN_REFUND,mGoodlist.get(position-2).getSeven_refund());
                bundle.putInt(GOODS_TIME_REFUND,mGoodlist.get(position-2).getTime_refund());
                bundle.putInt(GOODS_BOUGHT,mGoodlist.get(position-2).getBought());
                openActivity(DetailActivity.class,bundle);
            }
        });

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
//            User user = User.
            @Override
            public void onClick(View v) {
                ToastUtil.show(getActivity(),R.string.me_nologin_not_login);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onSucceed(int what, Response<String> response) {

    }

    @Override
    public void onFailed(int what, Response<String> response) {

    }
}
