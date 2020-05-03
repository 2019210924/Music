package com.example.heartmusic.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.heartmusic.R;
import com.example.heartmusic.view.activity.MainActivity;
import com.example.heartmusic.view.interfece.IMusicPlay;

import java.io.IOException;

/**
 * @author 珝珞
 * @date 2020/5/3
 * @project name HeartMusic
 */
public class MyMusicService extends Service {
    private final String TAG = MyMusicService.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 进程优先级别：前台进程，可视进程，服务进程，后台进程，空进程  （前台进程是最稳定，系统内存不足是先回收 空进程）
         *
         * 为什么要把服务Service提升为前台进程，在内存不足时，前台进程不会那么容易被系统回收
         *
         * 把 服务进程 提升到 前台进程 会自动绑定通知
         */

        // 需要用到通知，用户点击通知栏，就计划APP-->Activity

        // 设置事件信息，点击通知可以跳转到指定Activity
        Intent intent = new Intent(this, MainActivity.class);


        // 设置事件信息，点击通知可以跳转到指定Activity
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        // 设置通知显示相关信息
        Notification.Builder builder1 = new Notification.Builder(this);
        builder1.setSmallIcon(R.mipmap.ic_launcher); //设置图标

        builder1.setContentTitle("播放中"); //设置标题
        builder1.setContentText("我的音乐播放器"); //消息内容
        builder1.setWhen(System.currentTimeMillis()); //发送时间
        builder1.setDefaults(Notification.DEFAULT_ALL); //设置默认的提示音，振动方式，灯光
        builder1.setAutoCancel(true);//打开程序后图标消失

        // 延时意图，所谓延时意图就是不是马上执行，需要用户去点击后才执行，其实就是对Intent对封装
        PendingIntent pendingIntent =PendingIntent.getActivity(this, 0, intent, 0);
        builder1.setContentIntent(pendingIntent);
        Notification notification1 = builder1.build();
        notificationManager.notify(124, notification1); // 通过通知管理器发送通知

        // id=通知到唯一标示  notification=通知
        startForeground(1, builder1.getNotification());
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "绑定成功");
        return new PlayMusicBinder();
    }

    private MediaPlayer mediaPlayer;

    /**
     * 增强版Binder，扩展出播放音乐🎵行为
     */
    class PlayMusicBinder extends Binder implements IMusicPlay {

        public PlayMusicBinder() {
            mediaPlayer = new MediaPlayer();
        }

        /**
         * 播放音乐
         *
         * @param musicPath 音乐文件的路径
         */
        @Override
        public void playMusic(String musicPath) {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(musicPath);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 暂停播放
         */
        @Override
        public void pausedPlay() {
            mediaPlayer.pause();
        }

        /**
         * 继续播放
         */
        @Override
        public void continuePlay() {
            mediaPlayer.start();
        }

        /**
         * 停止播放
         */
        @Override
        public void stopPlay() {
            mediaPlayer.stop();
        }

        /**
         * 让Activity可以获取到服务使用到MediaPlayer
         *
         * @return
         */
        @Override
        public MediaPlayer getMediaPlayer() {
            return mediaPlayer;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "解绑成功");

        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release(); // 释放硬件播放资源
        }
        return super.onUnbind(intent);
    }
}

