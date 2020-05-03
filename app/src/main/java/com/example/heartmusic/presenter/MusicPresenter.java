package com.example.heartmusic.presenter;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.example.heartmusic.base.BasePresenter;
import com.example.heartmusic.contract.MusicContract;
import com.example.heartmusic.model.MusicModel;
import com.example.heartmusic.utils.httphelper.Request;
import com.example.heartmusic.view.activity.MusicActivity;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author 珝珞
 * @date 2020/5/2
 * @project name HeartMusic
 */
public class MusicPresenter extends BasePresenter<MusicContract.Model, MusicContract.View> implements MusicContract.Presenter {
    public MusicPresenter(MusicActivity view) {
        super(view);
    }

    @Override
    public MusicModel initModel(Handler handler) {
        return new MusicModel(handler);
    }

    @Override
    public void modelResponse(Message msg) {
        try {
            switch (msg.what) {
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
    public void getSongUrl(Request request) {
        mModel.getSongUrl(request);
    }
}
