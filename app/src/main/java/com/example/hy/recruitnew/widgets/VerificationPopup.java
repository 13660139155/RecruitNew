package com.example.hy.recruitnew.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import com.example.hy.recruitnew.R;

/**
 * 长按弹出框
 * Created by 陈健宇 at 2018/12/26
 */
public class VerificationPopup extends PopupWindow {

    private Context mContext;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    public VerificationPopup(Context context) {
        super(context);
        initPopup(context);
    }

    public void show(View achor){
        if(isShowing()) return;
        mProgressBar.setVisibility(View.VISIBLE);
        this.showAtLocation(achor, Gravity.CENTER, 0, 0);
    }

    public void cancelLoading(){
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    public ImageView getImageView(){
        return mImageView;
    }

    private void initPopup(Context context) {
        mContext = context;
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.pupup_verification, null);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setContentView(view);
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setOutsideTouchable(true);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.PopupAnim);

        mImageView = view.findViewById(R.id.iv_verification);
        mProgressBar = view.findViewById(R.id.progress_bar);
    }
}
