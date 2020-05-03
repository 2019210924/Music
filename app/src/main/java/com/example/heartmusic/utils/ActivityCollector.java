package com.example.heartmusic.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 珝珞
 * @date 2020/4/27
 * @project name BaseMvp
 */
public class ActivityCollector {
    public static List<Activity> activitys = new ArrayList<Activity>();
    /*添加activity*/
    public static void addActivity(Activity activity){
        activitys.add(activity);
    }

    /*移除activity*/
    public static void removeActivity(Activity activity){
        activitys.remove(activity);
    }
    /*清除所有activity*/
    public  static void finishAll(){
        for (Activity activity : activitys){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
