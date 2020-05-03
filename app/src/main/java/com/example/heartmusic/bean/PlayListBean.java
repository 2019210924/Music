package com.example.heartmusic.bean;

import java.util.List;

/**
 * @author 珝珞
 * @date 2020/5/2
 * @project name HeartMusic
 */
/*{
    "playlists": [
        {
            "name": "劳动节：致无名英雄，幸福并感激着", //歌单名字
            "id": 4984317152,				//歌单id，获取歌单详情相关
            ...
            "trackCount": 32,		//歌曲数
            "cloudTrackCount": 0,
            "coverImgUrl": "http://p1.music.126.net/-6MF3Ox61TUwcPjZh7Pguw==/109951164932914816.jpg",	//歌单封面
            "coverImgId": 109951164932914820,
            "description": "致无名英雄：\n\n在茫茫的人海中 不知你是哪一个\n在奔腾的浪花里 不知你是哪一朵\n在征服宇宙的大军里 是你默默奉献\n在辉煌事业的长河中 你在永远奔腾\n虽然我不认识你 也不知道你的名字\n但山知道你 江河知道你\n祖国不会忘记你\n\n幸福并感激着！",
            "tags": [
                "华语",
                "流行",
                "感动"
            ],
          	...

        }
    ],
    "total": 1297,
    "code": 200,
    "more": true,
    "cat": "全部"
}*/
public class PlayListBean {

    private String songName;
    private int songId;
    private int trackCount;
    private String coverImgUrl;
    private String tags;
    private List<CreatorBean> mCreatorBeans;

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public int getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<CreatorBean> getCreatorBeans() {
        return mCreatorBeans;
    }

    public void setCreatorBeans(List<CreatorBean> creatorBeans) {
        mCreatorBeans = creatorBeans;
    }

    public static class CreatorBean {

        /*"creator": {
                ...
                "avatarUrl": "http://p1.music.126.net/vercp6mHD9e_kUUKFBBo5A==/109951164508150919.jpg",
                ...
                "userId": 82179192,
                "userType": 200,
                "nickname": "艾琳艾德勒丶",	//歌单创建者
                "signature": "小艾琳 A.K.A Lil Irene\nBrainy is the new sexy.",
              	...
            }*/

        private String avatarUrl;
        private String userId;
        private String nickname;
        private String signature;

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
    }
}
