package com.example.heartmusic.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heartmusic.R;
import com.example.heartmusic.bean.SongStartBean;
import com.example.heartmusic.view.widget.MyImageView;

import java.util.List;

/**
 * @author 珝珞
 * @date 2020/5/2
 * @project name HeartMusic
 */
public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {
    private Context mContext;
    private List<SongStartBean> mData;
    private OnItemClickListener mListener;

    public MusicListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void refreshUi(List<SongStartBean> data){
        this.mData = data;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.music_list,null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        /*还有一个头像*/
        SongStartBean entity = mData.get(i);
        String author = TextUtils.isEmpty(entity.getAr()) ? "未知歌手" : entity.getAr();
        String title = entity.getName();
        String time = TextUtils.isDigitsOnly(entity.getTime()) ? "未知时间" : entity.getTime();
        String al = TextUtils.isDigitsOnly(entity.getAl()) ? "未知专辑" : entity.getAl();
        String picUrl = entity.getPic();
        viewHolder.tv_songwriter.setText(author);
        viewHolder.tv_songname.setText(title);
        viewHolder.tv_time.setText(time);
        viewHolder.tv_songal.setText(al);
        viewHolder.list_head.setImageURL(picUrl);
        viewHolder.rl_root.setTag(entity);
        viewHolder.rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongStartBean _bean = (SongStartBean) v.getTag();
                if (mListener != null){
                    mListener.onItemClick(_bean,i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mData == null){
            return 0;
        }
        return mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_songname;
        private TextView tv_songwriter;
        private TextView tv_time;
        private TextView tv_songal;
        private MyImageView list_head;
        private RelativeLayout rl_root;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_songname = itemView.findViewById(R.id.tv_songname);
            tv_songal = itemView.findViewById(R.id.tv_songal);
            tv_songwriter = itemView.findViewById(R.id.tv_songwriter);
            tv_time = itemView.findViewById(R.id.tv_time);
            list_head = itemView.findViewById(R.id.iv_list_head);
            rl_root = itemView.findViewById(R.id.rl_root);
        }
    }

    //自定义item点击事件
    public interface OnItemClickListener{
        void onItemClick(SongStartBean bean, int position);
    }
}

