package com.example.heartmusic.presenter;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.example.heartmusic.base.BasePresenter;
import com.example.heartmusic.bean.BannerBean;
import com.example.heartmusic.bean.ItemBean;
import com.example.heartmusic.contract.FirstContract;
import com.example.heartmusic.model.FirstModel;
import com.example.heartmusic.utils.httphelper.Request;
import com.example.heartmusic.view.fragment.FirstFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 珝珞
 * @date 2020/5/2
 * @project name HeartMusic
 */
public class FirstPresenter extends BasePresenter<FirstContract.Model, FirstContract.View> implements FirstContract.Presenter{

    public FirstPresenter(FirstFragment view) {
        super(view);
    }

    @Override
    public FirstModel initModel(Handler handler) {
        return new FirstModel(handler);
    }

    @Override
    public void modelResponse(Message msg) {
        try {
            switch (msg.what) {
                case 0://getBannerData接口请求后返回的
                    String data = (String) msg.obj;//取出接口数据
                    if (!TextUtils.isEmpty(data)) {
                        JSONObject jsonObject = new JSONObject(data);
                        if (jsonObject.has("code")) {
                            int code = jsonObject.getInt("code");
                            if (code == 200 && jsonObject.has("albums")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("albums");
                                List<BannerBean> list = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    BannerBean bannerBean = new BannerBean();
                                    if (obj.has("name")) {
                                        bannerBean.setAlbum_name(obj.getString("name"));
                                    }
                                    if (obj.has("picUrl")) {
                                        bannerBean.setPicUri(obj.getString("picUrl"));
                                    }
                                    list.add(bannerBean);
                                }
                                if (mView != null) {
                                    mView.onBannerData(list);
                                }
                            }
                        }
                    }
                    break;
                case 1://getListData接口请求后返回的
                    String data1 = (String) msg.obj;//取出接口数据
                    if (!TextUtils.isEmpty(data1)) {
                        JSONObject jsonObject = new JSONObject(data1);
                        if (jsonObject.has("code")) {
                            int code = jsonObject.getInt("code");
                            if (code == 200 && jsonObject.has("playlists")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("playlists");
                                List<ItemBean> list = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    ItemBean itemBean = new ItemBean();
                                    if (obj.has("name")) {
                                        itemBean.setTitle(obj.getString("name"));
                                    }
                                    if (obj.has("coverImgUrl")) {
                                        itemBean.setPicUrl(obj.getString("coverImgUrl"));
                                    }
                                    if (obj.has("creator")) {
                                        JSONObject dataObj = obj.getJSONObject("creator");
                                        if (dataObj.has("nickname")){
                                            itemBean.setWriter(dataObj.getString("nickname"));
                                        }
                                    }
                                    list.add(itemBean);
                                }
                                if (mView != null) {
                                    mView.onListData(list);
                                }
                            }
                        }
                    }

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getBannerData(Request request) {
        mModel.getBannerData(request);
    }

    @Override
    public void getListData(Request request) {
        mModel.getListData(request);
    }
}
