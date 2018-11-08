package com.example.hy.recruitnew;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.hy.recruitnew.util.StatusBarUtil;
import com.example.hy.recruitnew.util.ToastUtil;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    private static final long WAIT_TIME = 2000L; // 再点一次退出程序时间设置
    private static long TOUCH_TIME = 0;//第一次按下返回键的时间
    private Unbinder mUnbinder;

    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.btn_2)
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.compat(this);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_1)
    public void onViewClicked() {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_2)
    public void onViewClicked2() {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME){
            finish();
        }else {
            TOUCH_TIME = System.currentTimeMillis();
            ToastUtil.toast(this, getResources().getString(R.string.exit_app));
        }
    }


    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
