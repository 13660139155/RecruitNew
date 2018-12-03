package com.example.hy.recruitnew;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Explode;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import com.example.hy.recruitnew.adapter.BaseRvAdapter;
import com.example.hy.recruitnew.decoration.ItemDecoration;
import com.example.hy.recruitnew.manager.CenterLayoutManager;
import com.example.utilslibrary.StatusBarUtil;
import com.example.utilslibrary.ToastUtil;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    private static final long WAIT_TIME = 2000L; // 再点一次退出程序时间设置
    private static long TOUCH_TIME = 0;//第一次按下返回键的时间
    @BindView(R.id.fab_android)
    FloatingActionButton fabAndroid;
    @BindView(R.id.fab_background)
    FloatingActionButton fabBackground;
    @BindView(R.id.fab_front)
    FloatingActionButton fabFront;
    @BindView(R.id.fab_bigData)
    FloatingActionButton fabBigData;
    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    @BindView(R.id.fab_main)
    FloatingActionsMenu fabMain;


    private Unbinder mUnbinder;
    private BaseRvAdapter mBaseRvAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Explode());
        }
        setContentView(R.layout.activity_main);
        StatusBarUtil.compat(this);
        mUnbinder = ButterKnife.bind(this);

        mLinearLayoutManager = new CenterLayoutManager(this);
        mLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mBaseRvAdapter = new BaseRvAdapter();
        rvMain.setLayoutManager(mLinearLayoutManager);
        rvMain.setAdapter(mBaseRvAdapter);

        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(rvMain);
        rvMain.addItemDecoration(new ItemDecoration());
        mBaseRvAdapter.setItemClickListener(postion -> rvMain.smoothScrollToPosition(postion));

//        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
//        pagerSnapHelper.attachToRecyclerView(rvMain);

        rvMain.post(new Runnable() {
            @Override
            public void run() {
                rvMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                    }

                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int firstVisibleItemCompletePosition = mLinearLayoutManager.findFirstCompletelyVisibleItemPosition();
                        if (firstVisibleItemCompletePosition == 1) {
                            hideFloatingButton();
                        } else {
                            showFloatingButton();
                        }

                    }
                });
            }
        });

        fabAndroid.setOnClickListener(v -> startActivity(1));
        fabBackground.setOnClickListener(v -> startActivity(2));
        fabFront.setOnClickListener(v -> startActivity(3));
        fabBigData.setOnClickListener(v -> startActivity(4));

        fabMain.setTranslationY(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 400, getResources().getDisplayMetrics()));
    }


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            ToastUtil.toastInCenter(this, getResources().getString(R.string.exit_app), null);
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }

    /**
     * 显示floatingButton
     */
    @SuppressLint("RestrictedApi")
    private void showFloatingButton() {
        if (fabMain.getVisibility() == View.INVISIBLE) {
            fabMain.setVisibility(View.VISIBLE);
            fabMain.animate().setDuration(500).setInterpolator(new BounceInterpolator()).translationY(0).start();
        }
    }

    /**
     * 隐藏floatingButton
     */
    @SuppressLint("RestrictedApi")
    private void hideFloatingButton() {
        if (fabMain.getVisibility() == View.VISIBLE) {
            ViewPropertyAnimator animator = fabMain.animate().setDuration(300).setInterpolator(new AccelerateDecelerateInterpolator()).translationY(
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 400, getResources().getDisplayMetrics())
            );
            new Handler().postDelayed(() -> fabMain.setVisibility(View.INVISIBLE), 301);
            animator.start();
        }
    }

    /**
     * 获取item的顶部到Recyclerview可见区域的距离
     * @param scrollY 垂直滚动出Recyclerview可见区域的距离
     * @param itemHeight item高度
     * @param position item位置
     */
    private float getItemTop(float scrollY, float itemHeight, float position){
        return position * itemHeight - scrollY;
    }

    /**
     * 获取垂直滚动出Recyclerview可见区域的距离
     */
    private float getScrollYDistance(RecyclerView recyclerView){
        View firstVisibleItem = recyclerView.getChildAt(0);
        int itemHeight = firstVisibleItem.getMeasuredHeight();//得到item高度
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        assert manager != null;
        int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();//得到第一个item位置
        int firstVisibleBottom = manager.getDecoratedBottom(firstVisibleItem);//得到第一个item底部位置（距离父控件底部开始）
        return (firstVisibleItemPosition + 1) * itemHeight - firstVisibleBottom;
    }

    private void startActivity(int flag) {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            RegisterActivity.startActivityByExplode(this, flag);
        }else {
            RegisterActivity.startActivity(this, flag);
        }
    }

}
