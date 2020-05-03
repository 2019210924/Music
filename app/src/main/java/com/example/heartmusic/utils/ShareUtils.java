package com.example.heartmusic.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author 珝珞
 * @date 2020/4/27
 * @project name BaseMvp
 */
/*sharepreferences 封装*/
public class ShareUtils {
    public  static final String NAME = "config";

    /**
     * 存储string类型
     * @param context
     * @param key
     * @param value
     */
    public static void putString(Context context, String key,String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).commit();
    }

    /**
     * 获取string的值
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(Context context,String key,String defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,defValue);
    }

    /**
     * 存储Int类型的值
     * @param mContext this
     * @param key      key
     * @param value    要存储的Int值
     */
    public static void putInt(Context mContext, String key, int value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, value).commit();
    }


    /**
     * 获取Int类型的值
     * @param mContext this
     * @param key      key
     * @param defValue 默认值
     * @return
     */
    public static int getInt(Context mContext, String key, int defValue) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defValue);
    }


    /**
     * 存储Boolean类型的值
     * @param mContext this
     * @param key      key
     * @param value    要存储Boolean值
     */
    public static void putBoolean(Context mContext, String key, boolean value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    /**
     * 获取Boolean类型的值
     * @param mContext this
     * @param key      key
     * @param defValue 默认值
     * @return
     */
    public static boolean getBoolean(Context mContext, String key, Boolean defValue) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defValue);
    }

}
