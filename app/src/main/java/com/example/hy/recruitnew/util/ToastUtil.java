package com.example.hy.recruitnew.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hy.recruitnew.R;

/**
 * 提示微技巧
 * Created by 陈健宇 at 2018/10/27
 */
public class ToastUtil {

    private static Toast toast;

    /**
     * Toast提示
     * @param context
     * @param message 的内容
     */
    public static void showToast(Context context, String message){
        if(toast == null){
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }else {
            toast.setText(message);
        }
        toast.show();
    }

    /**
     * 自定义toast请求
     */
    @SuppressLint("ResourceAsColor")
    public static void toast(Context context, String messager){

        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        TextView textView = view.findViewById(R.id.tv_toast);
        textView.setText(messager);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }


    private ToastUtil() {
        throw new AssertionError();
    }
}
