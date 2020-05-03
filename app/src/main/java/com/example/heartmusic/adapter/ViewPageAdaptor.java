package com.example.heartmusic.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 珝珞
 * @date 2020/5/2
 * @project name HeartMusic
 */
//切换页面的适配器
public class ViewPageAdaptor extends FragmentPagerAdapter {
    //添加一个列表存放fragment
    private List<Fragment> mFragmentList = new ArrayList<>();
    public ViewPageAdaptor(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        //返回列表中第几个
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
//        返回列表的长度
        return mFragmentList.size();
    }
    //写一个方法添加fragment
  public void addFragment(Fragment fragment){
        mFragmentList.add(fragment);
  }
}
