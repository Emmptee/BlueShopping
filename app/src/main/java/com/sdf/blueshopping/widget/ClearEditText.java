package com.sdf.blueshopping.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AutoCompleteTextView;

import com.sdf.blueshopping.R;


public class ClearEditText extends android.support.v7.widget.AppCompatAutoCompleteTextView implements
        View.OnFocusChangeListener, TextWatcher {

    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    /**
     * 控件是否有焦点
     */
    private boolean mHasFoucs;

    private boolean mKeyboardShow = false;
    private OnKeyboardChangedListener mOnKeyboardChangedListener;
    private boolean mNeedAdjustWindow = false;
    private Context mContext;
    private ViewTreeObserver.OnGlobalLayoutListener mLayoutListener;

    public interface OnKeyboardChangedListener {
        void onKeyboardChanged(boolean shown);
    }

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        //这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        if (!isInEditMode()) {
            init();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void init() {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            // throw new NullPointerException("You can add drawableRight attribute in XML");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mClearDrawable = getResources().getDrawable(R.drawable.ic_clear_input_default, null);
            } else {
                mClearDrawable = getResources().getDrawable(R.drawable.ic_clear_input_default);
            }
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);

        mLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                getWindowVisibleDisplayFrame(r);

                int screenHeight = getRootView().getHeight();
                int heightDifference = screenHeight - (r.bottom - r.top);

                boolean visible = heightDifference > screenHeight / 3;
                if (mContext instanceof Activity) {
                    Activity activity = (Activity) mContext;

                    if (mNeedAdjustWindow) {
                        if (visible) {
                            if (!mKeyboardShow && isFullScreen(activity)) {
                                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                            }
                        } else {
                            if (mKeyboardShow && (!isFullScreen(activity))) {
                                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                            }
                        }
                    }
                }

                if (mKeyboardShow != visible) {
                    mKeyboardShow = visible;
                    if (mOnKeyboardChangedListener != null) {
                        mOnKeyboardChangedListener.onKeyboardChanged(mKeyboardShow);
                    }
                }
            }
        };

        if (mNeedAdjustWindow) {
            getViewTreeObserver().addOnGlobalLayoutListener(mLayoutListener);
        }
    }


    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {

                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */

    public void onFocusChange(View v, boolean hasFocus) {
        this.mHasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    public void setClearIconVisible(boolean visible) {
        Drawable right = visible && isEnabled() ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }


    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count,
                              int after) {
        if (mHasFoucs) {
            setClearIconVisible(s.length() > 0);
        }
    }


    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }


    public void afterTextChanged(Editable s) {

    }

    @Override
    public void setCursorVisible(boolean visible) {
        super.setCursorVisible(visible);
        if (!visible) {
            clearFocus();
        }
    }

    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    public void setOnKeyboardChangedListener(OnKeyboardChangedListener listener) {
        mOnKeyboardChangedListener = listener;
        getViewTreeObserver().addOnGlobalLayoutListener(mLayoutListener);
    }

    public static boolean isFullScreen(Activity activity) {
        int flag = activity.getWindow().getAttributes().flags;
        if ((flag & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
            return true;
        } else {
            return false;
        }
    }

}

