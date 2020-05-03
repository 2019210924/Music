package com.example.heartmusic.bean;

/**
 * @author 珝珞
 * @date 2020/5/3
 * @project name HeartMusic
 */
public class SongBean {
    String url;//音乐网址
    int fee;//是否付费 1付费 0不付费
    int id ;
    String writer;
    String album;
    String songname;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }
}
