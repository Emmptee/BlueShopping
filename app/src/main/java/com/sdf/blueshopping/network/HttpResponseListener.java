package com.sdf.blueshopping.network;

import android.content.Context;
import android.content.DialogInterface;

import com.sdf.blueshopping.R;
import com.sdf.blueshopping.ui.widget.WaitDialog;
import com.sdf.blueshopping.utils.ToastUtil;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.net.ProtocolException;

/**
 * Created by shidongfang on 2017/8/11.
 */

public class HttpResponseListener<T> implements OnResponseListener<T> {

    private Context mContext;
    private Request<T> mRequest;
    private HttpListener<T> mHttpListener;
    private WaitDialog mWaitDialog;
    private boolean isLoading;

    public HttpResponseListener(Context context,Request<T> request,HttpListener<T> httpListener,
                                boolean canCancel,boolean isLoading){
        mContext = context;
        mRequest = request;
        mHttpListener = httpListener;
        if (context != null && isLoading){
            mWaitDialog = new WaitDialog(context);
            mWaitDialog.setCancelable(canCancel);
            mWaitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mWaitDialog.cancel();
                }
            });
        }
    }

    @Override
    public void onStart(int what) {
        if (mWaitDialog != null && !mWaitDialog.isShowing() && isLoading){
            mWaitDialog.show();
        }
    }

    @Override
    public void onSucceed(int what, Response<T> response) {
        if (mContext != null){
            mHttpListener.onSucceed(what,response);
        }
    }

    @Override
    public void onFailed(int what, Response<T> response) {
        Exception exception = response.getException();
        if (exception instanceof NetworkError) {// 网络不好
            ToastUtil.show(mContext, R.string.error_please_check_network);
        } else if (exception instanceof TimeoutError) {// 请求超时
            ToastUtil.show(mContext, R.string.error_timeout);
        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
            ToastUtil.show(mContext, R.string.error_not_found_server);
        } else if (exception instanceof URLError) {// URL是错的
            ToastUtil.show(mContext, R.string.error_url_error);
        } else if (exception instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            ToastUtil.show(mContext, R.string.error_not_found_cache);
        } else if (exception instanceof ProtocolException) {
            ToastUtil.show(mContext, R.string.error_system_unsupport_method);
//        } else if (exception instanceof ParseError) {
//            ToastUtil.show(mContext, R.string.error_parse_data_error);
        } else {
            ToastUtil.show(mContext, R.string.error_unknow);
        }
        if (mContext != null) {
            mHttpListener.onFailed(what, response);
        }
    }

    @Override
    public void onFinish(int what) {
        if (mContext != null && mWaitDialog.isShowing()){
            mWaitDialog.show();
        }
    }
}
