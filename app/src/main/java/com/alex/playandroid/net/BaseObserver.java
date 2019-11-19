package com.alex.playandroid.net;

import android.util.Log;

import com.alex.playandroid.utils.LogUtil;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public abstract class BaseObserver<T> implements Observer<Response<T>> {

    private static final String TAG = "NetWorkError:";

    @Override
    public void onSubscribe(Disposable d) {}

    @Override
    public void onNext(Response<T> tResponse) {
        if (tResponse.getErrorCode() == 0) {
            onSuccess(tResponse);
        } else {
            onHandleSeverError(tResponse);
        }
    }

    /**
     * 处理后台错误
     *
     * @param tResponse
     */
    private void onHandleSeverError(Response<T> tResponse) {
        LogUtil.e(TAG, tResponse.getErrorMsg());
        SmartToast.error(tResponse.getErrorMsg());
        onFailed(tResponse.getErrorMsg());
    }

    @Override
    public void onError(Throwable e) {
        String error;
        if (e instanceof HttpException) {     //   HTTP错误
            error = "HTTP异常";
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            error = "连接异常";
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            error = "超时异常";
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            error = "解析异常";
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            e.printStackTrace();
            error = "证书验证失败";
        } else {
            error = "未知异常";
        }
        LogUtil.e(TAG, error+e.getMessage());
        SmartToast.error(e.getMessage());
        onFailed(e.getMessage());
    }

    @Override
    public void onComplete() {}


    protected abstract void onSuccess(Response<T> tResponse);

    protected abstract void onFailed(String msg);

}
