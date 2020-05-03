package com.example.heartmusic.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.heartmusic.utils.ActivityCollector;

import java.util.IllformedLocaleException;

/**
 * @author 珝珞
 * @date 2020/4/27
 * @project name BaseMvp
 */
public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IView {

    public P mPresenter;

    public abstract P initPresenter();

    //    绑定页面
    protected abstract void initView();

    protected abstract int getLayoutId();

    //    其他数据初始化
    protected abstract void initData();

    //    打log
    protected final String TAG = this.getClass().getSimpleName();

    //封装Toast对象
    private static Toast toast;
    public Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        context = this;
        //管理activity
        ActivityCollector.addActivity(this);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        } else {
            throw new IllformedLocaleException("You must return a right contentView layout resource Id");//扔错误提醒
        }
//        init view
        initView();
//        初始化mPresenter
        mPresenter = initPresenter();
//        绑定生命周期
        if (mPresenter != null) {
            getLifecycle().addObserver(mPresenter);
        }
        initData();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        解绑p层 避免内存泄漏
        if (mPresenter != null) {
            getLifecycle().removeObserver(mPresenter);
            mPresenter = null;
        }
//        activity管理
        ActivityCollector.removeActivity(this);
//        通知系统回收
        System.gc();
    }


    /**
     * 隐藏软键盘
     */
    public void hideSoftInput(){
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }


    /**
     * 显示软键盘
     */
    public void showSoftInput(){
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm){
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(),0);
        }
    }
}
