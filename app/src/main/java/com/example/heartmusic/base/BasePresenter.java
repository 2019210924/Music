package com.example.heartmusic.base;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<M extends IModel,V extends IView> implements IPresenter{
    public V mView;
    public M mModel;

    @SuppressLint("HandlerLink")
    public BasePresenter(V view) {
        this.mView = view;
        this.mModel = initModel(getHandler());
    }

    public abstract M initModel(Handler handler);

    /**
     * 获取handler的方法
     *
     * @return BaseHandler
     */
    public Handler getHandler(){
        return new BaseHandler(this);
    }

    /**
     * 基础Handler 用于P层与M层通信
     */
    public static class BaseHandler extends Handler {

        //弱引用Activity或者Fragment 避免Handler持有导致内存泄漏
        private final WeakReference<BasePresenter> presenter;

        public BaseHandler(BasePresenter presenter) {
            this.presenter = new WeakReference<>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            if(presenter.get()!=null&&presenter.get().mView!=null){
                presenter.get().modelResponse(msg);
            }
        }
    }

    //接收M层返回的消息
    public abstract void modelResponse(Message msg);

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void OnCreate(@NonNull LifecycleOwner owner){}

    /**
     *通过实现IPresenter中的OnDestroy方法来解除持有
     *
     * @param owner 生命周期管理者
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void OnDestroy(@NonNull LifecycleOwner owner){
        //解绑V层 避免导致内存泄漏
        mView = null;
        mModel.onDestroy();
        mModel = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void OnPause(@NonNull LifecycleOwner owner){}

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void OnResume(@NonNull LifecycleOwner owner){}

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void OnStart(@NonNull LifecycleOwner owner){}

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void OnStop(@NonNull LifecycleOwner owner){}

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void OnLifeCycleChanged(@NonNull LifecycleOwner owner){}
}
