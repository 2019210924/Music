package com.example.heartmusic.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.example.heartmusic.R;
import com.example.heartmusic.bean.ItemBean;

import java.util.List;
/**
 * @author 珝珞
 * @date 2020/5/2
 * @project name HeartMusic
 */
/*类似gridview形式*/
public class GridViewAdapter extends RecyclerViewBaseAdapter {

    public GridViewAdapter(List<ItemBean> data) {
        super(data);
    }

    @Override
    protected View getSubView(ViewGroup parent, int viewType) {
        //在这创建条目的UI
        View view = View.inflate(parent.getContext(), R.layout.item_grid_view, null);
        return view;
    }
}
