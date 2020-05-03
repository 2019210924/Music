package com.example.heartmusic.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author 珝珞
 * @date 2020/4/28
 * @project name BaseMvp
 */
public abstract class BaseFragment<P extends IPresenter> extends Fragment implements IView {
    public P mPresenter;

    public abstract P initPresenter();

    //baseFragment和baseActivity同样的道理
    protected abstract void initView(View view);

    protected abstract int getLayoutId();

    protected abstract void initData();

    protected final String TAG = this.getClass().getSimpleName();

    //封装Toast对象
    private static Toast toast;
    public Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        Log.d(TAG, "onAttach: ");
        super.onAttach(context);
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        Log.d(TAG, "onCreate: ");
//        super.onCreate(savedInstanceState);
//        //初始化mPresenter
//        mPresenter = initPresenter();
//        //绑定生命周期
//        getLifecycle().addObserver(mPresenter);
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = null;
        if (getLayoutId() != 0) {
            view = inflater.inflate(getLayoutId(), null, false);
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        initView(view);
        //初始化mPresenter
        mPresenter = initPresenter();
        initData();
//        绑定生命周期
        if (mPresenter != null) {
            getLifecycle().addObserver(mPresenter);
        }
        return view;
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach: ");
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
        //解绑P层 避免内存泄漏
        getLifecycle().removeObserver(mPresenter);
        mPresenter = null;
        //通知系统进行一次回收
        System.gc();
    }


    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: ");
        super.onActivityCreated(savedInstanceState);
    }

//    /**
//     * 土司  toast
//     */
//    @SuppressLint("ShowToast")
//    public void showToast(String msg) {
//        try {
//            if (toast == null) {
//                toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
//            } else {
//                toast.setText(msg);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Looper.prepare();
//            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
//            Looper.loop();
//        }
//    }
}