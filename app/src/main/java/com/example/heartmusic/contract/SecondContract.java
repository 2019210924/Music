package com.example.heartmusic.contract;

import com.example.heartmusic.base.IModel;
import com.example.heartmusic.base.IPresenter;
import com.example.heartmusic.base.IView;
import com.example.heartmusic.bean.SongStartBean;
import com.example.heartmusic.utils.httphelper.Request;

import java.util.List;

/**
 * @author 珝珞
 * @date 2020/5/2
 * @project name HeartMusic
 */
public interface SecondContract {
    interface Model extends IModel {
        void getSongListData(Request request);
        void getSongUrl(Request request);
    }

    interface View extends IView {
        void onSongListData(List<SongStartBean> data);
        void onSongUrl(String url);
    }

    interface Presenter extends IPresenter {
        void getSongListData(Request request);
        void getSongUrl(Request request);
    }
}
