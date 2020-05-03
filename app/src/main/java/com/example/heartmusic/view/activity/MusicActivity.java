package com.example.heartmusic.view.activity;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.heartmusic.R;
import com.example.heartmusic.base.BaseActivity;
import com.example.heartmusic.bean.SongStartBean;
import com.example.heartmusic.contract.MusicContract;
import com.example.heartmusic.presenter.MusicPresenter;
import com.example.heartmusic.service.MyMediaPlayer;
import com.example.heartmusic.service.MyMusicService;
import com.example.heartmusic.utils.ToastUtils;
import com.example.heartmusic.utils.httphelper.Request;
import com.example.heartmusic.view.interfece.IMusicPlay;
import com.example.heartmusic.view.widget.MyImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MusicActivity extends BaseActivity<MusicContract.Presenter> implements View.OnClickListener, MusicContract.View {
    private MyImageView AlPic;
    private
    ImageView lastOne,shop_continue,nextOne,playStyle;
    private TextView mTvSongName;
    private IMusicPlay iMusicPlay;

    private SeekBar mSeekBar;
    private TextView mTvStartTime;
    private TextView mTvEndTime;

    //add
    private int mCurrentPosition = 0;
    private List<SongStartBean> mDatas;
    private SongStartBean mCurrentData;

    private MyMediaPlayer mPlayer = MyMediaPlayer.getInstance();


    @Override
    public MusicPresenter initPresenter() {
        return new MusicPresenter(this);
    }

    @Override
    protected void initView() {
        AlPic = findViewById(R.id.iv_pic);
        lastOne = findViewById(R.id.lastone);
        shop_continue = findViewById(R.id.bofang);
        nextOne = findViewById(R.id.next);
        playStyle = findViewById(R.id.xunhuan);

        mTvSongName = findViewById(R.id.song_title);
        mSeekBar = findViewById(R.id.sb_progress);
        mTvStartTime = findViewById(R.id.tv_start);
        mTvEndTime = findViewById(R.id.tv_end);

        lastOne.setOnClickListener(this);
        shop_continue.setOnClickListener(this);
        nextOne.setOnClickListener(this);
        playStyle.setOnClickListener(this);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    mPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        initPlayerEvent();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_music;
    }

    @Override
    protected void initData() {
        if (getIntent().hasExtra("currentPosition")){
            mCurrentPosition = getIntent().getIntExtra("currentPosition",0);
        }
        if (getIntent().hasExtra("datas")){
            mDatas = getIntent().getParcelableArrayListExtra("datas");
        }
        if (mDatas != null && mDatas.size() > 0){
            mCurrentData = mDatas.get(mCurrentPosition);
            refreshUi();
        }
    }

    private void refreshUi(){
        String picUrl = mCurrentData.getPic();
        String songName = mCurrentData.getName();

        if (!TextUtils.isEmpty(picUrl)){
            AlPic.setImageURL(picUrl);
        }
        if (!TextUtils.isEmpty(songName)){
            mTvSongName.setText(songName);
        }
        if (mPlayer.isPlaying()){
            shop_continue.setImageResource(R.mipmap.stop);
            mTvEndTime.setText(formatTime(mPlayer.getDuration()));
            mSeekBar.setMax(mPlayer.getDuration());
            new Thread(new SeekBarThread()).start();
        }
    }

    class SeekBarThread implements Runnable {

        @Override
        public void run() {
            while (mPlayer.isPlaying()) {
                handler.sendEmptyMessage(mPlayer.getCurrentPosition());
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mSeekBar.setProgress(msg.what);
            mTvStartTime.setText(formatTime(msg.what));
        }
    };

    private String formatTime(int length) {
        Date date = new Date(length);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        String TotalTime = simpleDateFormat.format(date);
        return TotalTime;

    }

    @Override
    protected void onStart() {
        super.onStart();
        // 绑定服务
        bindService(new Intent(this, MyMusicService.class), connection, BIND_AUTO_CREATE);
    }


    private ServiceConnection connection = new ServiceConnection() {
        /**
         * 连接到服务
         * @param name
         * @param service
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMusicPlay = (IMusicPlay) service;
        }

        /**
         * 断开连接
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解绑服务：注意bindService后 必须要解绑服务，否则会报 连接资源异常
        if (null != connection) {
            unbindService(connection);
        }
    }

    /**
     * 用户按返回键，系统会调用到此方法
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);

        // 判断是否在播放，如果在播放中，才告知用户 弹出对话框
        if (iMusicPlay == null) {
            return true;
        }

        MediaPlayer mediaPlayer = iMusicPlay.getMediaPlayer();

        if(mediaPlayer.isPlaying()) {

            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    showAlertDialog();
                    break;
            }
        }
        return true;
    }

    /**
     * 弹出对话框
     */
    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MusicActivity.this);
        builder.setTitle("提示");
        builder.setMessage("确定要关闭音乐播放？");
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNeutralButton("后台播放", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 既然是后台播放，就是要把当前Activity切换到后台
                moveTaskToBack(true);
            }
        });
        builder.setPositiveButton("取消", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lastone:
                if (mCurrentPosition == 0) {
                    ToastUtils.show(this, "已经是第一首了，没有上一曲！");
                    return;
                }
                mCurrentPosition --;
                SongStartBean lastBean = mDatas.get(mCurrentPosition);
                getSongUrl(lastBean);
                break;
            case R.id.bofang:
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                } else {
                    mPlayer.resume();
                }
                break;
            case R.id.next:
                if (mCurrentPosition == mDatas.size() - 1) {
                    ToastUtils.show(this, "已经是最后一首了，没有下一曲！");
                    return;
                }
                mCurrentPosition ++;
                SongStartBean songStartBean = mDatas.get(mCurrentPosition);
                getSongUrl(songStartBean);
                break;
            case R.id.xunhuan:
                break;
        }
    }


    private void getSongUrl(SongStartBean bean) {
        mTvSongName.setText(bean.getName());
        Request request = new Request.Builder("http://47.99.165.194/song/url?id=" + bean.getId()).setMethod("GET").build();
        mPresenter.getSongUrl(request);
    }


    @Override
    public void onSongUrl(String url) {
        Log.e("onSongUrl", "------" + url);
        if (TextUtils.isEmpty(url) || url.equals("null")) {
            ToastUtils.show(this, "歌曲链接地址失效");
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
                shop_continue.setImageResource(R.mipmap.stop);
                mTvEndTime.setText(formatTime(mPlayer.getDuration()));
                mSeekBar.setMax(mPlayer.getDuration());
                new Thread(new SeekBarThread()).start();
            }

            @Override
            public void pause() {
                shop_continue.setImageResource(R.mipmap.start);
            }

            @Override
            public void stop() {
                shop_continue.setImageResource(R.mipmap.start);
            }

            @Override
            public void reset() {

            }

            @Override
            public void resume() {
                shop_continue.setImageResource(R.mipmap.stop);
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
