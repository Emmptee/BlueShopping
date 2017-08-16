package com.sdf.blueshopping.ui.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sdf.blueshopping.R;
import com.sdf.blueshopping.entity.GoodsInfo;

import java.util.List;

/**
 * Created by shidongfang on 2017/8/16.
 */

public class GoodsListAdapter extends BaseAdapter {

    private Context mContext;
    private List<GoodsInfo.ResultBean.GoodlistBean> mGoodList;
    private int mHeaderCount;

    public GoodsListAdapter(Context context, List<GoodsInfo.ResultBean.GoodlistBean> goodslist,int headercount){
        mContext = context;
        mGoodList = goodslist;
        mHeaderCount = headercount;
    }
    @Override
    public int getCount() {
        return mGoodList.size();
    }

    @Override
    public Object getItem(int position) {
        return mGoodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_good_list,null);
            holder.goodPhoto = (SimpleDraweeView) convertView.findViewById(R.id.good_photo);
            holder.goodIcon = (ImageView) convertView.findViewById(R.id.good_icon);
            holder.goodAppointmentImg = (ImageView) convertView.findViewById(R.id.good_appointment_img);
            holder.goodLoading = (ProgressBar) convertView.findViewById(R.id.good_loading);
            holder.goodDistance = (TextView) convertView.findViewById(R.id.good_tv_distance);
            holder.goodTitle = (TextView) convertView.findViewById(R.id.good_tv_title);
            holder.goodFreshOrderLayout = (LinearLayout) convertView.findViewById(R.id.good_fresh_order_layout);
            holder.goodDescription = (TextView) convertView.findViewById(R.id.good_tv_description);
            holder.goodCommentScore = (RatingBar) convertView.findViewById(R.id.good_comment_score);
            holder.goodPrice = (TextView) convertView.findViewById(R.id.good_tv_price);
            holder.goodValue = (TextView) convertView.findViewById(R.id.good_tv_value);
            holder.goodValue.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//添加删除线
            holder.goodCount = (TextView) convertView.findViewById(R.id.good_tv_count);
            holder.goodArea = (TextView) convertView.findViewById(R.id.good_tv_area);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.goodPhoto.setImageURI(Uri.parse(mGoodList.get(position).getImages().get(0).getImage()));
        holder.goodIcon.setVisibility(View.VISIBLE);
        if (mGoodList.get(position).getIs_appointment() == 1) {
            holder.goodAppointmentImg.setVisibility(View.VISIBLE);
        } else {
            holder.goodAppointmentImg.setVisibility(View.GONE);
        }
        holder.goodLoading.setVisibility(View.GONE);
        holder.goodDistance.setText(mGoodList.get(position).getDistance());
        holder.goodTitle.setText(mGoodList.get(position).getProduct());
        if (mGoodList.get(position).getIs_new().equals("0")) {
            holder.goodFreshOrderLayout.setVisibility(View.GONE);
        } else {
            holder.goodFreshOrderLayout.setVisibility(View.VISIBLE);
        }
        holder.goodDescription.setText(mGoodList.get(position).getTitle());
        holder.goodCommentScore.setVisibility(View.GONE);
        holder.goodPrice.setText(mGoodList.get(position).getPrice());
        holder.goodValue.setText(mGoodList.get(position).getValue());
        holder.goodCount.setText("已售"+mGoodList.get(position).getBought()+"份");
//        holder.goodArea.setText();
        return convertView;
        }



    static class ViewHolder
    {
        public SimpleDraweeView goodPhoto;
        public ImageView goodIcon;
        public ImageView goodAppointmentImg;
        public ProgressBar goodLoading;

        public TextView goodDistance;
        public TextView goodTitle;
        public LinearLayout goodFreshOrderLayout;
        public TextView goodDescription;
        public RatingBar goodCommentScore;
        public TextView goodPrice;
        public TextView goodValue;
        public TextView goodCount;
        public TextView goodArea;
    }
}
