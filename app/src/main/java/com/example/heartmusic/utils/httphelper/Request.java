package com.example.heartmusic.utils.httphelper;

/*包含网络请求信息*/
public class Request{
    private String url;//目标网址
    private String method;
    private String[] Key;
    private String[] Value;

    private CallBack callback;



    private String contentType;


//回调接口
    public void setCallback(CallBack callback) {
        this.callback = callback;
    }



    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String[] getKey() {
        return Key;
    }

    public String[] getValue() {
        return Value;
    }





    /*构造器*/
    public Request(Builder builder){
        this.url = builder.url;
        this.method = builder.method;

        this.Key = builder.Key;
        this.Value = builder.Value;

    }

    public String getContentType() {
        return contentType;
    }


    /* 建造者模式  建一个内部类*/
    public static class Builder{

        private String url;
        private String method = "GET";
        private String [] Key = null;
        private String [] Value = null;

        //数据是个普通表单
        private String contentType = "application/x-www-form-urlencoded";
       // private String contentType = "multipart/form-data";//数据里有文件
       // private String contentType = "application/json";//数据是个json


        //给他们赋值（建造者模式）
        public Builder(String url){
            this.url = url;
        }

        public Builder setMethod(String method){
            this.method = method;
            return this;
        }
        public Builder setKeyArray(String[] Key){
            this.Key = Key;
            return this;
        }
        public Builder setValueArray(String[] Value){
            this.Value = Value;
            return this;
        }
        public Builder setContentType(String contentType){
            this.contentType = contentType;
            return this;
        }

        public Request build(){
            return new Request(this);
        }


    }

}


