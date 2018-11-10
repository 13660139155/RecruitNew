package com.example.hy.recruitnew;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by 陈健宇 at 2018/11/10
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //第一：默认初始化
        Bmob.initialize(this, "b646b3a9edf3f3a741ccc96d429b428b");
    }
}
