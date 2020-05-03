package com.example.heartmusic.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.heartmusic.R;
import com.example.heartmusic.bean.ItemBean;
import com.example.heartmusic.view.widget.MyImageView;

import java.util.List;

/**
 * @author 珝珞
 * @date 2020/5/2
 * @project name HeartMusic
 */

public abstract class RecyclerViewBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final List<ItemBean> mData;
    private OnItemClickListener mOnItemClickListener;

    public RecyclerViewBaseAdapter(List<ItemBean> data) {
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getSubView(parent, viewType);
        return new InnerHolder(view);
    }

    protected abstract View getSubView(ViewGroup parent, int viewType);

    /**
     * 这个方法是用于绑定holder的,一般用来设置数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //在这里设置数据
        ((InnerHolder) holder).setData(mData.get(position), position);
    }


    /**
     * 反回条目的个数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        //设置一个监听,其实,就是要设置一个接口,一个回调的接口
        this.mOnItemClickListener = listener;
    }


    /**
     * 编写回调的步骤
     * 1、创建这个接口
     * 2、定义借口内部的方法
     * 3、提供设置接口的方法(其实是外部实现)
     * 4、接口方法的调用
     */


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {


//        private ImageView mIcon;
        private MyImageView mIcon;
        private TextView mTitle;
        private TextView mWriter;
        private int mPosition;

        public InnerHolder(View itemView) {
            super(itemView);

            //找到条目的控件
            mIcon =  itemView.findViewById(R.id.icon);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mWriter = (TextView) itemView.findViewById(R.id.writer);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(mPosition);
                    }
                }
            });
        }

        /**
         * 这个方法用于设置数据
         */
        public void setData(ItemBean itemBean, int position) {

            this.mPosition = position;
            //开始设置数据
//            mIcon.setImageResource(itemBean.icon);
            mIcon.setImageURL(itemBean.picUrl);
            mTitle.setText(itemBean.title);
            mWriter.setText(itemBean.writer);
        }
    }
}
