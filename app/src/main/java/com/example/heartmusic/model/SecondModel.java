package com.example.heartmusic.model;

import android.os.Handler;
import android.os.Message;

import com.example.heartmusic.base.BaseModel;
import com.example.heartmusic.contract.SecondContract;
import com.example.heartmusic.utils.httphelper.CallBack;
import com.example.heartmusic.utils.httphelper.NetUtil;
import com.example.heartmusic.utils.httphelper.Request;

/**
 * @author 珝珞
 * @date 2020/5/2
 * @project name HeartMusic
 */
public class SecondModel extends BaseModel implements SecondContract.Model {
    public SecondModel(Handler handler) {
        super(handler);
    }

    @Override
    public void getSongListData(Request request) {
        NetUtil.getInstance().execute(request, new CallBack() {
            @Override
            public void onResponse(String response) {
                Message message = new Message();
                message.obj = response;
                message.what = 0x01;
                sendMessage(message);
            }

            @Override
            public void onFailed(Throwable throwable) {
                throwable.printStackTrace();

            }
        });
    }

    @Override
    public void getSongUrl(Request request) {
        NetUtil.getInstance().execute(request, new CallBack() {
            @Override
            public void onResponse(String response) {
                Message message = new Message();
                message.obj = response;
                message.what = 0x02;
                sendMessage(message);
            }

            @Override
            public void onFailed(Throwable throwable) {
                throwable.printStackTrace();

            }
        });
    }
}
