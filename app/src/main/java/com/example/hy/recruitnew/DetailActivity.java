package com.example.hy.recruitnew;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.DisplayCutout;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.b.V;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.iv_detail)
    ImageView ivDetail;
    @BindView(R.id.cl_reveal)
    ConstraintLayout clReveal;
    @BindView(R.id.btn_android)
    Button btnAndroid;
    @BindView(R.id.btn_background)
    Button btnBackground;
    @BindView(R.id.btn_front)
    Button btnFront;
    @BindView(R.id.btn_bigData)
    Button btnBigData;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.ctl_bar)
    CollapsingToolbarLayout ctlBar;

    private Unbinder mUnbinder;
    private static final int ANIM = 500;
    private static final String KEY_TEXT = "keyText";
    private int flag = -1;
    private boolean isVisible;
    private String detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (intent != null) {
            flag = intent.getIntExtra(KEY_TEXT, -1);
        }

        setText(flag);
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP)
            ivDetail.getDrawable().mutate().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);

        fab.setOnClickListener(v -> {
            if (flag == 0)
                launchRevealAnimation();
            else
                startActivity(flag);
        });

        btnAndroid.setOnClickListener(v -> startActivity(1));
        btnBackground.setOnClickListener(v -> startActivity(2));
        btnFront.setOnClickListener(v -> startActivity(3));
        btnBigData.setOnClickListener(v -> startActivity(4));

        toolbar.setNavigationOnClickListener(v -> {
            if(isVisible)
                launchRevealAnimation();
            else
                finish();

        });

        isVisible = clReveal.getVisibility() == View.VISIBLE;
    }

    private void startActivity(int flag) {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            RegisterActivity.startActivityByExplode(this, flag);
        }else {
            RegisterActivity.startActivity(this, flag);
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(isVisible)
            launchRevealAnimation();
        else
            super.onBackPressed();
    }

    /**
     * 启动动画
     */
    private void launchRevealAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animation animation = clReveal.getAnimation();
            if (animation != null) return;
            int x = fab.getLeft() + fab.getWidth() / 2;
            int y = fab.getTop() + fab.getHeight() / 2;
            int radius = (int) Math.hypot(ivDetail.getWidth(), ivDetail.getHeight());
            if (isVisible) {//隐藏
                ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(ivDetail, "alpha", 0, 1);
                alphaAnimation.setDuration(ANIM);
                Animator animator = ViewAnimationUtils.createCircularReveal(clReveal, x, y, radius, 0);
                animator.setDuration(ANIM);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        clReveal.setVisibility(View.INVISIBLE);
                        animator.removeListener(this);
                        ctlBar.setTitle(detail);
                    }
                });
                animator.start();
                alphaAnimation.start();
                isVisible = false;
            } else {//显示
                ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(ivDetail, "alpha", 1, 0);
                alphaAnimation.setDuration(ANIM);
                Animator animator = ViewAnimationUtils.createCircularReveal(clReveal, x, y, 0, radius);
                animator.setDuration(ANIM);
                animator.addListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        clReveal.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animator.removeListener(this);
                    }
                });
                animator.start();
                alphaAnimation.start();
                isVisible = true;
                ctlBar.setTitle(" ");
            }
        } else {
            ObjectAnimator showAnim = ObjectAnimator.ofFloat(ivDetail, "alpha", 0, 1);
            ObjectAnimator hideAnim = ObjectAnimator.ofFloat(clReveal, "alpha", 1, 0);
            showAnim.setDuration(ANIM);
            hideAnim.setDuration(ANIM);
            if (isVisible) {
                isVisible = false;

                hideAnim.removeAllListeners();
                hideAnim.setTarget(clReveal);
                hideAnim.start();
                hideAnim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        clReveal.setVisibility(View.INVISIBLE);
                    }
                });

                ivDetail.setVisibility(View.VISIBLE);
                showAnim.removeAllListeners();
                showAnim.setTarget(ivDetail);
                showAnim.start();
                ctlBar.setTitle(detail);
            } else {
                isVisible = true;

                hideAnim.removeAllListeners();
                hideAnim.setTarget(ivDetail);
                hideAnim.start();
                hideAnim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ivDetail.setVisibility(View.INVISIBLE);
                    }
                });

                clReveal.setVisibility(View.VISIBLE);
                showAnim.removeAllListeners();
                showAnim.setTarget(clReveal);
                showAnim.start();

                ctlBar.setTitle("  ");
            }
        }

    }

    /**
     * 根据flag设置文本
     */
    private void setText(int flag) {
        switch (flag) {
            case 0:
                detail = getString(R.string.main_rdc);
                tvDetail.setText(R.string.detail_rdc);
                ivDetail.setImageResource(R.drawable.logo_rdc);
                ctlBar.setTitle(detail);
                break;
            case 1:
                detail = getString(R.string.main_android);
                tvDetail.setText(R.string.detail_android);
                ivDetail.setImageResource(R.drawable.logo_android);
                ctlBar.setTitle(getString(R.string.main_android));
                break;
            case 2:
                detail = getString(R.string.main_background);
                tvDetail.setText(R.string.detail_background);
                ivDetail.setImageResource(R.drawable.logo_background);
                ctlBar.setTitle(getString(R.string.main_background));
                break;
            case 3:
                detail = getString(R.string.main_front);
                tvDetail.setText(R.string.detail_front);
                ivDetail.setImageResource(R.drawable.logo_front);
                ctlBar.setTitle(getString(R.string.main_front));
                break;
            case 4:
                detail = getString(R.string.main_bigData);
                tvDetail.setText(R.string.detail_bigData);
                ivDetail.setImageResource(R.drawable.logo_bigdata);
                ctlBar.setTitle(getString(R.string.main_bigData));
                break;
            default:
                break;
        }
    }

    public static void startActivity(Context context, int flag) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(KEY_TEXT, flag);
        context.startActivity(intent);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void startActivityByShare(Context activity, int flag, View view) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(KEY_TEXT, flag);
        activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) activity, view, view.getTransitionName()).toBundle());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void startActivityByShareMore(Context activity, int flag, View... view) {
        Intent intent = new Intent(activity, DetailActivity.class);
        View view1 = view[0];
        View view2 = view[1];
        intent.putExtra(KEY_TEXT, flag);
        activity.startActivity(
                intent,
                ActivityOptions.makeSceneTransitionAnimation((Activity) activity,
                Pair.create(view1, view1.getTransitionName()),
                Pair.create(view2, view2.getTransitionName())).toBundle()
                );

    }
}
