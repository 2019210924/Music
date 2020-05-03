package com.example.heartmusic.presenter;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.example.heartmusic.base.BasePresenter;
import com.example.heartmusic.bean.SongStartBean;
import com.example.heartmusic.contract.SecondContract;
import com.example.heartmusic.model.SecondModel;
import com.example.heartmusic.utils.httphelper.Request;
import com.example.heartmusic.view.fragment.SecondFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 珝珞
 * @date 2020/5/2
 * @project name HeartMusic
 */
public class SecondPresenter extends BasePresenter<SecondContract.Model, SecondContract.View> implements SecondContract.Presenter {
    public SecondPresenter(SecondFragment view) {
        super(view);
    }

    @Override
    public SecondModel initModel(Handler handler) {
        return new SecondModel(handler);
    }

    @Override
    public void modelResponse(Message msg) {
        try {
            switch (msg.what) {
                case 0x01://接口请求后返回的
                    String data = (String) msg.obj;//取出接口数据
                    if (!TextUtils.isEmpty(data)) {
                        JSONObject jsonObject = new JSONObject(data);
                        if (jsonObject.has("code")) {
                            int code = jsonObject.getInt("code");
//                    int fee = jsonObject.getInt("fee");
                            if (code == 200 && jsonObject.has("playlist")) {
                                JSONObject object = jsonObject.getJSONObject("playlist");
                                if (object.has("tracks")) {
                                    JSONArray jsonArray = object.getJSONArray("tracks");
                                    List<SongStartBean> list = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject obj = jsonArray.getJSONObject(i);
                                        SongStartBean songStartBean = new SongStartBean();
                                        if (obj.has("name")) {
                                            songStartBean.setName(obj.getString("name"));
                                        }
                                        if (obj.has("id")) {
                                            songStartBean.setId(obj.getInt("id"));
                                        }
                                        if (obj.has("dt")) {
                                            String time = (obj.getInt("dt") / 1000 / 60) + ":" + (obj.getInt("dt") / 1000 % 60);
                                            songStartBean.setTime(time);
                                        }
                                        if (obj.has("ar")) {
                                            JSONArray jsonArray1 = obj.getJSONArray("ar");
                                            if (jsonArray1.length() > 0) {
                                                JSONObject obj1 = jsonArray1.getJSONObject(0);
                                                if (obj1.has("name")) {
                                                    String arName = obj1.getString("name");
                                                    songStartBean.setAr(arName);
                                                }
                                            }
                                        }
                                        if (obj.has("al")) {
                                            JSONObject obj1 = obj.getJSONObject("al");
                                            if (obj1.has("name")) {
                                                String alName = obj1.getString("name");
                                                songStartBean.setAl(alName);
                                            }
                                            if (obj1.has("picUrl")) {
                                                String picUrl = obj1.getString("picUrl");
                                                songStartBean.setPic(picUrl);
                                            }
                                        }

                           /* if (obj.has("ar")) {
                                JSONArray jsonArray1 = jsonObject.getJSONArray("ar");
                                List<SongStartBean.arBean> list1 = new ArrayList<>();
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    JSONObject obj1 = jsonArray.getJSONObject(j);
                                    SongStartBean.arBean arBean1 = new SongStartBean.arBean();
                                    if (obj1.has("name")) {
                                        songStartBean.setAr(obj1.getString("name"));
                                    }
                                    list1.add(arBean1);
                                }
                            }
                            if (obj.has("al")) {
                                JSONArray jsonArray1 = jsonObject.getJSONArray("al");
                                List<SongStartBean.arBean> list1 = new ArrayList<>();
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    JSONObject obj1 = jsonArray.getJSONObject(j);
                                    SongStartBean.arBean arBean1 = new SongStartBean.arBean();
                                    if (obj1.has("name")) {
                                        songStartBean.setAr(obj1.getString("name"));
                                    }
                                    list1.add(arBean1);
                                }*/
//                            }
                                        list.add(songStartBean);
                                    }
                                    if (mView != null) {
                                        mView.onSongListData(list);
                                    }
                                }
                            }
                        }
                    }
                    break;
                case 0x02:
                    String res = (String) msg.obj;
                    if (!TextUtils.isEmpty(res)){
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.has("code")) {
                            int code = jsonObject.getInt("code");
                            if (code == 200 && jsonObject.has("data")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                if (jsonArray.length() > 0){
                                    JSONObject obj = jsonArray.getJSONObject(0);
                                    if (obj.has("url")){
                                        String url= obj.getString("url");
                                        if (!TextUtils.isEmpty(url) && mView != null){
                                            mView.onSongUrl(url);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getSongListData(Request request) {
        mModel.getSongListData(request);
    }

    @Override
    public void getSongUrl(Request request) {
        mModel.getSongUrl(request);
    }
}
