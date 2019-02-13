package com.example.hy.recruitnew.widgets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hy.recruitnew.R;
import com.example.hy.recruitnew.http.AllApi;
import com.example.hy.recruitnew.http.bean.VerificationData;

import androidx.core.content.ContextCompat;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.106.131.6/user/getCheckPicture/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AllApi allApi = retrofit.create(AllApi.class);
        allApi.getVerificationData().enqueue(new Callback<VerificationData>() {
            @Override
            public void onResponse(Call<VerificationData> call, Response<VerificationData> response) {
                VerificationData verificationData = response.body();
                Glide.with(mContext).load(verificationData.getBigPicture()).into(mImageView);
                VerificationPopup.this.showAtLocation(achor, Gravity.CENTER, 0, 0);
            }

            @Override
            public void onFailure(Call<VerificationData> call, Throwable t) {
                Log.d("rain", "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
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
