package com.example.heartmusic.model;

import android.os.Handler;
import android.os.Message;

import com.example.heartmusic.base.BaseModel;
import com.example.heartmusic.contract.FirstContract;
import com.example.heartmusic.utils.httphelper.CallBack;
import com.example.heartmusic.utils.httphelper.NetUtil;
import com.example.heartmusic.utils.httphelper.Request;
/**
 * @author 珝珞
 * @date 2020/5/2
 * @project name HeartMusic
 */

/*model类的思想就是请求数据*/
public class FirstModel extends BaseModel implements FirstContract.Model {
    public FirstModel(Handler handler) {
        super(handler);
    }

    @Override
    public void getBannerData(Request request) {
        NetUtil.getInstance().execute(request, new CallBack() {
            @Override
            public void onResponse(String response) {
                Message message = new Message();
                message.what = 0;
                message.obj = response;
                sendMessage(message);
            }

            @Override
            public void onFailed(Throwable throwable) {
                throwable.printStackTrace();

            }

        });
    }

    @Override
    public void getListData(Request request) {
        NetUtil.getInstance().execute(request, new CallBack() {
            @Override
            public void onResponse(String response) {
                Message message = new Message();
                message.what = 1;
                message.obj = response;
                sendMessage(message);
            }

            @Override
            public void onFailed(Throwable throwable) {
                throwable.printStackTrace();

            }

        });
    }
}
