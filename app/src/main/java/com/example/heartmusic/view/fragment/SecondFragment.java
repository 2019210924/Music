package com.example.heartmusic.view.fragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.heartmusic.R;
import com.example.heartmusic.adapter.MusicListAdapter;
import com.example.heartmusic.base.BaseFragment;
import com.example.heartmusic.bean.SongBean;
import com.example.heartmusic.bean.SongStartBean;
import com.example.heartmusic.contract.SecondContract;
import com.example.heartmusic.presenter.SecondPresenter;
import com.example.heartmusic.service.MyMediaPlayer;
import com.example.heartmusic.utils.ToastUtils;
import com.example.heartmusic.utils.httphelper.Request;
import com.example.heartmusic.view.activity.MusicActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 歌单详情页
 * <p>
 * 1.专辑
 * 2.列表
 */
public class SecondFragment extends BaseFragment<SecondContract.Presenter> implements SecondContract.View, View.OnClickListener {
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private MusicListAdapter mAdapter;//列表适配器
    private int mPageId = 0;//页码
    private boolean isRefresh = false;//标记是否触发下拉刷新
    private List<SongStartBean> mSongStartBeans = new ArrayList<>();

    //    数据源
    List<SongBean> mDatas;

    private ImageView nextIv, playIv, lastIv;
    private TextView singerTv, songTv;

    //    记录当前正在播放的音乐的位置
    int currnetPlayPosition = -1;
    //    记录暂停音乐时进度条的位置
    int currentPausePositionInSong = 0;
    MediaPlayer mediaPlayer;

    private MyMediaPlayer mPlayer = MyMediaPlayer.getInstance();

    @Override
    public SecondPresenter initPresenter() {
        return new SecondPresenter(this);
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = view.findViewById(R.id.music_list);
        mRefreshLayout = view.findViewById(R.id.sec_refresh_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new MusicListAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        //下拉刷新控件设置事件
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //触发下拉在最顶部了，页码置为0，数据清空，重新获取数据
                isRefresh = true;
                mPageId = 0;
                mSongStartBeans.clear();
                getSongData();
            }
        });

        nextIv = view.findViewById(R.id.music_bottom_iv_next);
        playIv = view.findViewById(R.id.music_bottom_iv_play);
        lastIv = view.findViewById(R.id.music_bottom_iv_last);
        singerTv = view.findViewById(R.id.music_bottom_tv_singer);
        songTv = view.findViewById(R.id.music_bottom_tv_song);
        //add
        RelativeLayout rl_bottom_menu = view.findViewById(R.id.music_bottomlayout);
        rl_bottom_menu.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_second;
    }

    @Override
    protected void initData() {
        getSongData();


        /*设置点击事件*/
        nextIv.setOnClickListener(this);
        lastIv.setOnClickListener(this);
        playIv.setOnClickListener(this);

        mediaPlayer = new MediaPlayer();
//        设置每一项的点击事件
        setEventListener();

        //add
        initPlayerEvent();
    }


    @Override
    public void onSongListData(List<SongStartBean> data) {
        if (data != null && data.size() > 0) {
            mSongStartBeans.addAll(data);
            mAdapter.refreshUi(mSongStartBeans);
            //设置控件结束动画
            if (isRefresh) {
                isRefresh = false;
                mRefreshLayout.setRefreshing(false);
            }
        }
    }

    private void getSongData() {
        Request request = new Request.Builder("http://47.99.165.194/playlist/detail?id=3100484976").setMethod("GET").build();
        System.out.println(request);
        mPresenter.getSongListData(request);
    }


    private void setEventListener() {
        /* 设置每一项的点击事件*/
        mAdapter.setOnItemClickListener(new MusicListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SongStartBean bean, int position) {
                currnetPlayPosition = position;
//                SongBean musicBean = mDatas.get(position);
                SongStartBean musicBean = mSongStartBeans.get(position);
//                playMusicInMusicBean(musicBean);
                getSongUrl(musicBean);
            }
        });
    }

    public void playMusicInMusicBean(String url) {

        stopMusic();
//                重置多媒体播放器
        mediaPlayer.reset();
//                设置新的播放路径
        try {
            mediaPlayer.setDataSource(url);
            playMusic();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 点击播放按钮播放音乐，或者暂停从新播放
     * 播放音乐有两种情况：
     * 1.从暂停到播放
     * 2.从停止到播放
     * */
    private void playMusic() {
        /* 播放音乐的函数*/
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            if (currentPausePositionInSong == 0) {
                try {
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
//                从暂停到播放
                mediaPlayer.seekTo(currentPausePositionInSong);
                mediaPlayer.start();
            }
            playIv.setImageResource(R.mipmap.stop);
        }
    }

    private void pauseMusic() {
        /* 暂停音乐的函数*/
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            currentPausePositionInSong = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
            playIv.setImageResource(R.mipmap.stop);
        }
    }

    private void stopMusic() {
        /* 停止音乐的函数*/
        if (mediaPlayer != null) {
            currentPausePositionInSong = 0;
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
            mediaPlayer.stop();
            playIv.setImageResource(R.mipmap.start);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMusic();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //上一曲
            case R.id.music_bottom_iv_last:
                if (currnetPlayPosition == 0) {
                    ToastUtils.show(getActivity(), "已经是第一首了，没有上一曲！");
                    return;
                }
                currnetPlayPosition = currnetPlayPosition - 1;
                SongStartBean lastBean = mSongStartBeans.get(currnetPlayPosition);

                getSongUrl(lastBean);
                break;
            //下一曲
            case R.id.music_bottom_iv_next:
                if (currnetPlayPosition == mDatas.size() - 1) {
                    ToastUtils.show(getActivity(), "已经是最后一首了，没有下一曲！");
                    return;
                }
                currnetPlayPosition = currnetPlayPosition + 1;
                SongStartBean songStartBean = mSongStartBeans.get(currnetPlayPosition);
                getSongUrl(songStartBean);
                break;
            //播放
            case R.id.music_bottom_iv_play:
                if (currnetPlayPosition == -1) {
//                    并没有选中要播放的音乐
                    Toast.makeText(getActivity(), "请选择想要播放的音乐！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                } else {
                    mPlayer.resume();
                }
                break;

                //底部菜单
            case R.id.music_bottomlayout:
                Intent intent = new Intent();
                intent.setClass(getActivity(), MusicActivity.class);
                intent.putParcelableArrayListExtra("datas", (ArrayList<? extends Parcelable>) mSongStartBeans);
                intent.putExtra("currentPosition",currnetPlayPosition);
                startActivity(intent);
                break;

        }
    }


    private void getSongUrl(SongStartBean bean) {
        /*根据传入对象播放音乐*/
        //设置底部显示的歌手名称和歌曲名
        singerTv.setText(bean.getAr());
        songTv.setText(bean.getName());

        Request request = new Request.Builder("http://47.99.165.194/song/url?id=" + bean.getId()).setMethod("GET").build();
        mPresenter.getSongUrl(request);
    }


    @Override
    public void onSongUrl(String url) {
        Log.e("onSongUrl", "------" + url);
        if (TextUtils.isEmpty(url) || url.equals("null")) {
            Toast.makeText(getActivity(), "歌曲链接地址失效", Toast.LENGTH_LONG).show();
            return;
        }
        mPlayer.setNetPath(url);
        mPlayer.start();
    }

    private void initPlayerEvent() {
        mPlayer.setMediaPlayFunctionListener(new MyMediaPlayer.MediaPlayFunctionListener() {
            @Override
            public void prepared() {

            }

            @Override
            public void start() {
                playIv.setImageResource(R.mipmap.stop);
            }

            @Override
            public void pause() {
                playIv.setImageResource(R.mipmap.start);
            }

            @Override
            public void stop() {
                playIv.setImageResource(R.mipmap.start);
            }

            @Override
            public void reset() {

            }

            @Override
            public void resume() {
                playIv.setImageResource(R.mipmap.stop);
            }
        });

        mPlayer.setMediaPlayInfoListener(new MyMediaPlayer.MediaPlayInfoListener() {
            @Override
            public void onError(MediaPlayer mp, int what, int extra) {

            }

            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

            }

            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void onSeekComplete(MediaPlayer mediaPlayer) {

            }

            @Override
            public void onSeekBarProgress(int progress) {

            }
        });
    }
}
