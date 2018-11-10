package com.example.hy.recruitnew.anim;

/**
 * 动画进度计算
 * Created by 陈健宇 at 2018/11/10
 */
public class TrunProcess {
    /** *
     *  返回动画完成的进度
     *  @param itemTop
     *  @param turningLine
     *  @param itemHeight
     *  @return
     */
    public static int getProcess(float itemTop, float turningLine, float itemHeight) {
        if (turningLine < itemTop || turningLine > (itemHeight + itemTop)){
            return 0;
        } else {
            float percent = (turningLine - itemTop) / itemHeight * 2;
            return (int) (percent * 100);
        }
    }
}
