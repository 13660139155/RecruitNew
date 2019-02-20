package com.example.hy.recruitnew;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchActivity extends AppCompatActivity implements Animation.AnimationListener {

    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.iv_recruit)
    ImageView ivRecruit;
    @BindView(R.id.tv_recruit)
    TextView tvRecruit;
    @BindView(R.id.gl_v)
    Guideline glV;
    @BindView(R.id.iv_rdc)
    ImageView ivRdc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_launch);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            View decorView = getWindow().getDecorView();
            decorView.post(() -> {
                DisplayCutout cutout = decorView.getRootWindowInsets().getDisplayCutout();
                if(cutout != null && cutout.getBoundingRects() != null){
                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                    lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                    getWindow().setAttributes(lp);
                }
            });
        }
        ButterKnife.bind(this);

        AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
        aa.setDuration(3000);
        aa.setAnimationListener(this);
        ivBg.startAnimation(aa);

        ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(1200);
        ivRecruit.startAnimation(sa);

        RotateAnimation ra = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(1200);
        tvRecruit.startAnimation(ra);

        TranslateAnimation ta = new TranslateAnimation(0, 0, 1500, 0);
        ta.setDuration(1200);
        ta.setInterpolator(new FastOutSlowInInterpolator());
        ivRdc.startAnimation(ta);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_open_enter, R.anim.anim_open_exit);
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
