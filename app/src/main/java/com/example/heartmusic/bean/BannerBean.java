package com.example.heartmusic.bean;

/**
 * @author 珝珞
 * @date 2020/5/2
 * @project name HeartMusic
 */

/*请求url: /album/newest
请求方式：GET*/
/*{
    "code": 200,
    "albums": [
        {
            "name": "暗示",
            "id": 88627345,//专辑的id，获取专辑详情相关
            "type": "EP/Single",
            "picUrl": "http://p3.music.126.net/1UWVS742R3IBkoY1UsQZhw==/109951164943562862.jpg",//要显示的图片
            "publishTime": 1588089600000,
            "artist": {
                "name": "周深",
                "id": 1030001,
                "picId": 109951164854660830,
                "img1v1Id": 18686200114669624,
                "briefDesc": "",
                "picUrl": "http://p3.music.126.net/u7I488Oi44qH4mqQuqFnqA==/109951164854660837.jpg",
                "img1v1Url": "http://p4.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
                "albumSize": 64,
                "alias": [],
                "trans": "",
                "musicSize": 263,


}*/
public class BannerBean {
    private String album_name;
    private int id;
    private int type;
    private String picUri;
    private String publishTime;
    private String artist_name;

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPicUri() {
        return picUri;
    }

    public void setPicUri(String picUri) {
        this.picUri = picUri;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }



}
