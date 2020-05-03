package com.example.heartmusic.view.fragment;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heartmusic.R;
import com.example.heartmusic.adapter.GridViewAdapter;
import com.example.heartmusic.base.BaseFragment;
import com.example.heartmusic.bean.BannerBean;
import com.example.heartmusic.bean.ItemBean;
import com.example.heartmusic.contract.FirstContract;
import com.example.heartmusic.presenter.FirstPresenter;
import com.example.heartmusic.utils.httphelper.Request;
import com.example.heartmusic.view.widget.MyLooperView;

import java.util.List;

/**首页
 *
 *
 * 1.轮播图
 *
 *
 * * 2.recyclerview 仿gradview
 *
 */
public class FirstFragment extends BaseFragment<FirstContract.Presenter> implements FirstContract.View {

    private MyLooperView mMyLooperView;
    private List<ItemBean> mItemBeans;
    private RecyclerView mRecyclerView;
    private GridViewAdapter mGridViewAdapter;

    @Override
    public FirstPresenter initPresenter() {
        return new FirstPresenter(this);
    }

    @Override
    protected void initView(View view) {
        mMyLooperView = view.findViewById(R.id.fir_banner);
        mRecyclerView = view.findViewById(R.id.rv_list);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_first;
    }

    @Override
    protected void initData() {
        //获取banner数据
        getBannerData();
        getListData();
    }

    private void getListData() {
        Request request = new Request.Builder("http://47.99.165.194/top/playlist?id=4984317152")
                .setMethod("GET").build();
        mPresenter.getListData(request);
    }

    private void getBannerData() {
        Request request = new Request.Builder("http://47.99.165.194/album/newest?id=88627345").setMethod("GET").build();
        mPresenter.getBannerData(request);
    }

    @Override
    public void onBannerData(List<BannerBean> data) {
        if (data != null && data.size() > 0){
            mMyLooperView.refreshUi(data);
        }
    }

    @Override
    public void onListData(List<ItemBean> data) {
        if (data != null && data.size() > 0) {
            mGridViewAdapter = new GridViewAdapter(data);
            mRecyclerView.setAdapter(mGridViewAdapter);
        }
    }
}
