package com.example.heartmusic.presenter;

import android.os.Handler;
import android.os.Message;

import com.example.heartmusic.base.BasePresenter;
import com.example.heartmusic.contract.MainContract;
import com.example.heartmusic.model.MainModel;

/**
 * @author 珝珞
 * @date 2020/4/28
 * @project name BaseMvp
 */
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> implements MainContract.Presenter {
    public MainPresenter(MainContract.View view) {
        super(view);
    }

    @Override
    public MainContract.Model initModel(Handler handler) {
        return new MainModel(handler);
    }

    @Override
    public void modelResponse(Message msg) {

    }
}
