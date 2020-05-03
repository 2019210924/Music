package com.example.heartmusic.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * @author 珝珞
 * @date 2020/4/28
 * @project name BaseMvp
 */
/*土司*/
public class ToastUtils {
    private static Toast mToast;//public改为private

    /**
     * 传入文字,在中间显示
     * */
    public static void show(Context context, String text){
        if (mToast == null){
            mToast = Toast.makeText( context, text , Toast.LENGTH_LONG);
        }else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            mToast.setText(text);
        }
        mToast.setGravity(Gravity.CENTER , 0 , 0);
        mToast.show();
    }
    /**
     * 传入资源文件,在中间显示
     * */
    public static void show(Context context,int resId){
        if (mToast == null){
            mToast = Toast.makeText( context, resId , Toast.LENGTH_LONG);
        }else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            mToast.setText(resId);
        }
        mToast.setGravity(Gravity.CENTER , 0 , 0);
        mToast.show();
    }

    /**
     * 传入文字，图片
     * */
    public static void showImg( Context context , String text , int resImg){

    // 获得toast内容
            mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
    // 创建Layout，并设置为水平布局
            LinearLayout mLayout = new LinearLayout(context);
            mLayout.setOrientation(LinearLayout.VERTICAL);
            ImageView mImage = new ImageView(context);
    // 用于显示图像的ImageView
            mImage.setImageResource(resImg);
            View toastView = mToast.getView();
    // 获取显示文字的Toast View
            mLayout.addView(mImage);
    // 添加到Layout
            mLayout.addView(toastView);
    // 设置Toast显示的View(上面生成的Layout).
            mToast.setView(mLayout);
        mToast.show();
    }

}
