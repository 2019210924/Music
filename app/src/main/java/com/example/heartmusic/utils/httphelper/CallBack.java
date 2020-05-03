package com.example.heartmusic.utils.httphelper;

//网络请求回调接口
public interface CallBack {
    void onResponse(String response);//请求正确
    void onFailed(Throwable throwable);//请求错误
}
