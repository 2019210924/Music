package com.example.heartmusic.contract;

import com.example.heartmusic.base.IModel;
import com.example.heartmusic.base.IPresenter;
import com.example.heartmusic.base.IView;
import com.example.heartmusic.utils.httphelper.Request;

/**
 * @author 珝珞
 * @date 2020/5/2
 * @project name HeartMusic
 */
public interface MusicContract {
    interface Model extends IModel {
        void getSongUrl(Request request);
    }

    interface View extends IView {
        void onSongUrl(String url);
    }

    interface Presenter extends IPresenter {
        void getSongUrl(Request request);
    }
}
