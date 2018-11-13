package com.example.hy.recruitnew.anim;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义列表加载动画
 * Created by 陈健宇 at 2018/11/10
 */
public class CustomAnimation {

    //非法值
    private static final int VAL = -1;

    // 动画进度
    private float mProcess = 0;
    private float mHalf = VAL;

    public CustomAnimation(){
    }

    /**
     * 通过进度控制动画
     */
    public void setAnimByProcess(View view, float process){
        if (view == null) return;

        //控制透明度
        float alpha;
        if (process < 0.5){
            alpha = 0.5f;
        }else if(process > 0.9){
            alpha = 1f;
        }else {
            alpha = process;
        }
        view.setAlpha(alpha);

        //控制大小
        float scale;
        if (process < 0.8){
            scale = 0.8f;
            view.setScaleX(scale);
            view.setScaleY(scale);
        }else if(process > 0.9){
            view.setScaleX(process);
            view.setScaleY(process);
            if(process < 1f){
                alpha = 1f;
                view.setScaleX(alpha);
                view.setScaleY(alpha);
            }

        }else {
            scale = process;
            view.setScaleX(scale);
            view.setScaleY(scale);
        }

    }

    /** *
     *  返回动画完成的进度
     */
    public float getProcess(float itemHalf, float turningLine, float recyclerHeight) {
        float process;
        process = (recyclerHeight - Math.abs(itemHalf - turningLine)) / recyclerHeight;
        Log.d("rain", "process: " + process);
        return process;
    }
}
