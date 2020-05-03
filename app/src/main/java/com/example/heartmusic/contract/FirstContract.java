package com.example.heartmusic.contract;

import com.example.heartmusic.base.IModel;
import com.example.heartmusic.base.IPresenter;
import com.example.heartmusic.base.IView;
import com.example.heartmusic.bean.BannerBean;
import com.example.heartmusic.bean.ItemBean;
import com.example.heartmusic.utils.httphelper.Request;

import java.util.List;

/**
 * @author 珝珞
 * @date 2020/4/30
 * @project name BaseMvp
 */
public interface FirstContract {
    interface Model extends IModel {
         void getBannerData(Request request);
         void getListData(Request request);
    }
    interface View extends IView {
        //banner数据回调 这里用接口返回的BannerBean
        void onBannerData(List<BannerBean> data);
        //首页专辑回调
        void onListData(List<ItemBean> data);

    }
    interface Presenter extends IPresenter {
        //请求banner数据
        void getBannerData(Request request);
        //请求首页专辑数据
        void getListData(Request request);
    }
}
