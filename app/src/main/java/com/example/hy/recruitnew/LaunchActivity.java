package com.example.hy.recruitnew;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.annotation.Annotation;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchActivity extends AppCompatActivity implements Animation.AnimationListener {

    private static final int ANIM_TIME = 1500;

    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.iv_recruit)
    ImageView ivRecruit;
    @BindView(R.id.tv_recruit)
    TextView tvRecruit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);

        AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
        aa.setDuration(ANIM_TIME * 2);
        aa.setAnimationListener(this);
        ivBg.startAnimation(aa);

        ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(ANIM_TIME);
        sa.setInterpolator(new BounceInterpolator());
        ivRecruit.startAnimation(sa);

        RotateAnimation ra = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(ANIM_TIME);
        tvRecruit.startAnimation(ra);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
