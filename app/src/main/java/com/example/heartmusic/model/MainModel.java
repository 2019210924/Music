package com.example.heartmusic.model;

import android.os.Handler;

import com.example.heartmusic.base.BaseModel;
import com.example.heartmusic.contract.MainContract;

/**
 * @author 珝珞
 * @date 2020/4/28
 * @project name BaseMvp
 */
public class MainModel extends BaseModel implements MainContract.Model {
    public MainModel(Handler handler) {
        super(handler);
    }
}
