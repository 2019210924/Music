package com.example.heartmusic.utils;

import android.content.Context;

/**
 * @author 珝珞
 * @date 2020/5/2
 * @project name HeartMusic
 */
public class SizeUtils {

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}