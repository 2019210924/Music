package com.example.heartmusic.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author 珝珞
 * @date 2020/5/2
 * @project name HeartMusic
 */
public class SongStartBean implements Parcelable {
    private String name;
    int id;
    int fee;
    String ar;
    String al;
    String time;
    private String pic;

    public SongStartBean() {
    }

    protected SongStartBean(Parcel in) {
        name = in.readString();
        id = in.readInt();
        fee = in.readInt();
        ar = in.readString();
        al = in.readString();
        time = in.readString();
        pic = in.readString();
    }

    public static final Creator<SongStartBean> CREATOR = new Creator<SongStartBean>() {
        @Override
        public SongStartBean createFromParcel(Parcel in) {
            return new SongStartBean(in);
        }

        @Override
        public SongStartBean[] newArray(int size) {
            return new SongStartBean[size];
        }
    };

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getAr() {
        return ar;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }

    public String getAl() {
        return al;
    }

    public void setAl(String al) {
        this.al = al;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeInt(fee);
        dest.writeString(ar);
        dest.writeString(al);
        dest.writeString(time);
        dest.writeString(pic);
    }

//    public static class arBean{
//        String writerName;
//
//        public String getWriterName() {
//            return writerName;
//        }
//
//        public void setWriterName(String writerName) {
//            this.writerName = writerName;
//        }
//    }
//
//    public static class alBean {
//        String alName;
//
//        public String getAlName() {
//            return alName;
//        }
//
//        public void setAlName(String alName) {
//            this.alName = alName;
//        }
//
//
//    }

}
