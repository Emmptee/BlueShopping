package com.sdf.blueshopping.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;

import com.sdf.blueshopping.R;

import static android.R.attr.radius;

/**
 * Created by shidongfang on 2017/8/14.
 */

public class Indicator extends View {


    /**前景画笔*/
    private Paint mForePaint;
    /**背景画笔*/
    private Paint mBgPaint;
    /**偏移值*/
    private float mOffset;
    /**小圆点个数*/
    private int number = 3;
    /**小圆点半径*/
    private int radius = 10;
    /**前景色*/
    private int mForeColor = Color.BLUE;
    /**背景色*/
    private int mBgColor = Color.RED;


    public Indicator(Context context) {
        super(context);
        initPaint();
    }
    new
    new AlertDialog.Builder

    public Indicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Indicator);
        number = typedArray.getInteger(R.styleable.Indicator_indicator_number,number);
        radius = typedArray.getInteger(R.styleable.Indicator_indicator_radius,radius);
        mForeColor = typedArray.getColor(R.styleable.Indicator_indicator_foreColor, mForeColor);
        mBgColor = typedArray.getColor(R.styleable.Indicator_indicator_bgColor, mBgColor);
        initPaint();
    }


    private void initPaint() {
        mForePaint = new Paint();
        mForePaint.setAntiAlias(true);
        mForePaint.setStyle(Paint.Style.FILL);
        mForePaint.setColor(mForeColor);
        mForePaint.setStrokeWidth(2);

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setColor(mForeColor);
        mBgPaint.setStrokeWidth(2);
    }
}
