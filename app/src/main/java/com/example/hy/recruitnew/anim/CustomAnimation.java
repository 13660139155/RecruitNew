package com.example.hy.recruitnew.anim;

import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义列表加载动画
 * Created by 陈健宇 at 2018/11/10
 */
public class CustomAnimation {

    //没有控件
    private static final int NO_VIEW = -1;
    //控件id
    private int mViewID = NO_VIEW;
    // 动画进度
    private int mProcess = 0;

    private boolean enableAlpha = true;
    private boolean enableScale = true;

    public CustomAnimation(){
    }

    /**
     * 通过进度控制动画
     * @param viewGroup 父容器
     * @param process 动画变化进度
     */
    public void setAnimByProcess(ViewGroup viewGroup, int process){
        if (viewGroup == null) return;
        mProcess = process;

        //控制透明度
        if (enableAlpha && mViewID != NO_VIEW) {
            View view = viewGroup.findViewById(mViewID);
            if (process > 0 && process <= 25){
                float alpha = (25 - process) / 25.0f;
                view.setAlpha(alpha);
            }else if(process > 75 && process <= 100){
                float alpha = (process - 75) / 25.0f;
                view.setAlpha(alpha);
            }
        }

        //控制大小
        if(enableScale && mViewID != NO_VIEW){
            View view = viewGroup.findViewById(mViewID);

        }
    }

    public int getViewId() {
        return mViewID;
    }

    public void setViewId(int viewId) {
        this.mViewID = viewId;
    }
}
