package com.example.heartmusic.view.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.heartmusic.R;
import com.example.heartmusic.bean.BannerBean;
import com.example.heartmusic.utils.SizeUtils;

import java.util.List;


/**
 * @author 珝珞
 * @date 2020/5/2
 * @project name HeartMusic
 */
public class MyLooperView extends LinearLayout {
    private MyViewPager mViewPager;
    private TextView mTitleView;
    private LinearLayout mPointContainer;
    private InnerPageAdapter mInnerAdapter = null;
    private Context mContext;
    private List<BannerBean> mData;

    public MyLooperView(Context context) {
        //确保统一入口
        this(context,null);
    }

    public MyLooperView(Context context, @Nullable AttributeSet attrs) {
        //确保统一入口
        this(context,attrs,0);
    }

    public MyLooperView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        this.mContext = context;
        //点容器，需要动态地创建，因为点的个数跟内容个数有关系
        LayoutInflater.from(context).inflate(R.layout.layout_looper_view,this,true);
        initView();
        initEvent();
    }

    /**
     * 设置相关事件
     */
    private void initEvent() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels) {
                //滑动时的回调
            }

            @Override
            public void onPageSelected(int position) {
                //这样来设置专辑名
                String album_name = mData.get(position % mInnerAdapter.getDataSize()).getAlbum_name();
                if (!TextUtils.isEmpty(album_name)){
                    mTitleView.setText(album_name);
                }
                updateIndicator();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //滑动状态的改变，有停止的，滑动中的.
                //ViewPager#SCROLL_STATE_IDLE
                //ViewPager#SCROLL_STATE_DRAGGING
                //ViewPager#SCROLL_STATE_SETTLING
            }
        });
    }

    /**
     * 找到子控件
     */
    private void initView() {
        mViewPager = findViewById(R.id.content_pager);
        mViewPager.setPageMargin(SizeUtils.dip2px(getContext(),20));
        mViewPager.setOffscreenPageLimit(3);
        mTitleView = this.findViewById(R.id.content_title);
        mPointContainer = this.findViewById(R.id.content_point_container);

        //适配器创建放在这
        mInnerAdapter = new InnerPageAdapter();
        mViewPager.setAdapter(mInnerAdapter);
    }

    public void refreshUi(List<BannerBean> data){
        this.mData = data;
        mInnerAdapter.notifyDataSetChanged();
        updateIndicator();
    }

    private void updateIndicator() {
        if(mInnerAdapter != null) {
            //先删除
            mPointContainer.removeAllViews();
            int indicatorSize = mInnerAdapter.getDataSize();
            for(int i = 0; i < indicatorSize; i++) {
                View view = new View(getContext());
                if((mViewPager.getCurrentItem() % mInnerAdapter.getDataSize() == i)) {
                    view.setBackgroundColor(Color.parseColor("#ff0000"));
                } else {
                    view.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                LayoutParams layoutParams = new LayoutParams(SizeUtils.dip2px(getContext(),5), SizeUtils.dip2px(getContext(),5));
                layoutParams.setMargins(SizeUtils.dip2px(getContext(),5),0, SizeUtils.dip2px(getContext(),5),0);
                view.setLayoutParams(layoutParams);
                //添加到容器里
                mPointContainer.addView(view);
            }
        }
    }

    private class InnerPageAdapter extends PagerAdapter {
        public int getDataSize(){
            if (mData == null){
                return 0;
            }
            return mData.size();
        }

        @Override
        public int getCount() {
            if (mData == null){
                return 0;
            }
            //因为要无限轮播，所以我们就给一个IntegerMaxValue
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view,@NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            //载入view，由外面给进来。只要对position进行一个转换
            int itemPosition = position % getDataSize();
            View itemView = getItemView(container,itemPosition);
            container.addView(itemView);
            return itemView;
        }

        public View getItemView(ViewGroup container,int itemPosition){
            MyImageView imageView = new MyImageView(container.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            String picUrl = mData.get(itemPosition).getPicUri();
            //加载网络图片
            if (!TextUtils.isEmpty(picUrl)){
                imageView.setImageURL(picUrl);
            }
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container,int position,@NonNull Object object) {
            container.removeView((View) object);
        }
    }

}
