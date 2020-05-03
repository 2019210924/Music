package com.example.heartmusic.view.interfece;

import android.media.MediaPlayer;

/**
 * @author 珝珞
 * @date 2020/5/3
 * @project name HeartMusic
 */
public interface IMusicPlay {
    /**
     * 播放音乐
     * @param musicPath 音乐文件的路径
     */
    void playMusic(String musicPath);

    /**
     * 暂停播放
     */
    void pausedPlay();

    /**
     * 继续播放
     */
    void continuePlay();

    /**
     * 停止播放
     */
    void stopPlay();

    /**
     * 让Activity可以获取到服务使用到MediaPlayer
     * @return
     */
    MediaPlayer getMediaPlayer();
}