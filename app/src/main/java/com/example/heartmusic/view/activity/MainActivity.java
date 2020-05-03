package com.example.heartmusic.view.activity;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.example.heartmusic.R;
import com.example.heartmusic.adapter.ViewPageAdaptor;
import com.example.heartmusic.base.BaseActivity;
import com.example.heartmusic.contract.MainContract;
import com.example.heartmusic.presenter.MainPresenter;
import com.example.heartmusic.view.fragment.FirstFragment;
import com.example.heartmusic.view.fragment.SecondFragment;
import com.example.heartmusic.view.fragment.ThirdFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View{
    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView navigation;
    //        设置滑动切换页面
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.navigation_first:
                            viewPager.setCurrentItem(0);
                            return true;
                        case R.id.navigation_second:
                            viewPager.setCurrentItem(1);
                            return true;
                        case R.id.navigation_third:
                            viewPager.setCurrentItem(2);
                            return true;
                    }
                    return false;
                }
            };

    @Override
    public MainContract.Presenter initPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.vp1);
        navigation = findViewById(R.id.nav_view);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        //让fragment与下面栏关联
        setupViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null)
                    menuItem.setChecked(false);
                else navigation.getMenu().getItem(0).setChecked(false);
                menuItem = navigation.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    //添加fragment
    private void setupViewPager(ViewPager viewPager) {
        /*在这里创建Fragment。。*/
        FirstFragment firstFragment = new FirstFragment();
        SecondFragment secondFragment = new SecondFragment();
        ThirdFragment thirdFragment = new ThirdFragment();

        ViewPageAdaptor adaptor = new ViewPageAdaptor(getSupportFragmentManager());
        /*在这里添加Fragment。。*/
        adaptor.addFragment(firstFragment);
        adaptor.addFragment(secondFragment);
        adaptor.addFragment(thirdFragment);
        viewPager.setAdapter(adaptor);
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.navigation_first:
                viewPager.setCurrentItem(0);
                break;
            case R.id.navigation_second:
                viewPager.setCurrentItem(1);
                break;
            case R.id.navigation_third:
                viewPager.setCurrentItem(2);
                break;

        }
        return super.onOptionsItemSelected(item);
    }*/
}

