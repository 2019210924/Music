package com.example.heartmusic.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.heartmusic.R;
import com.example.heartmusic.base.BaseActivity;
import com.example.heartmusic.base.IPresenter;
import com.example.heartmusic.utils.ToastUtils;

/**
 * @author 珝珞
 * @date 2020/5/1
 * @project name HeartMusic
 */
public class LoginActivity extends BaseActivity{
    private Button login,test1,test2;
    private TextView test3;
    @Override
    public IPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        login = findViewById(R.id.l_redButton);
        test1 = findViewById(R.id.l_FaceBookButton);
        test2 = findViewById(R.id.l_TwitterButton);
        test3 = findViewById(R.id.l_tv_forgotPassword);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        test3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(LoginActivity.this,"下个版本实现吧！");
            }
        });
        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(LoginActivity.this,"下个版本实现吧！");
            }
        });
        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(LoginActivity.this,"下个版本实现吧！");
            }
        });
    }
}
