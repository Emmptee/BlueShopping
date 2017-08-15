package com.sdf.blueshopping.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
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
    private int radius = 20;
    /**前景色*/
    private int mForeColor = Color.BLUE;
    /**背景色*/
    private int mBgColor = Color.RED;


    public Indicator(Context context) {
        super(context);
        initPaint();
    }

    public Indicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Indicator);
        number = typedArray.getInteger(R.styleable.Indicator_indicator_number,number);
        radius = typedArray.getInteger(R.styleable.Indicator_indicator_radius,radius);
        mForeColor = typedArray.getColor(R.styleable.Indicator_indicator_foreColor, mForeColor);
        mBgColor = typedArray.getColor(R.styleable.Indicator_indicator_bgColor, mBgColor);
        initPaint();
    }


    /**
     * 画笔
     */
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

    /**
     * 设定偏移量
     * @param position 位置
     * @param positionOffset 便宜百分比
     *
     */
    public void setmOffset(int position,float positionOffset){
        position %= number;
        mOffset = (position + positionOffset) * number * radius;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int start = width/2 - (radius*2*number+radius*(number-1))/2 +radius;
        for (int i = 0; i < number; i++) {
            //圆心y坐标需要加上strokeWidth/2，因为绘圆绘的是中心圆（半径=外圆半径-strokeWidth/2）
            canvas.drawCircle(start + radius*number*i,radius+1,radius,mBgPaint);
        }
        canvas.drawCircle(start + mOffset,radius+1, radius, mForePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));
    }
    private int measureHeight(int heightMeasureSpec){
        int result = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }else if (specMode == MeasureSpec.AT_MOST){
            result = radius = radius *2 +2;
        }else {
            result = 100;

        }
        return result;
    }


    private int measureWidth(int widthMeasureSpec){
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            //小圆点总直径 + 圆点间间距 + 画笔宽度（strokeWidth/2 * 2）
            result = radius * 2 * number + radius * (number-1) + 2;
        } else {
            result = 100;
        }

        return result;
    }
}
