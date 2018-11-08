package com.example.hy.recruitnew;

import android.os.Bundle;
import android.widget.Button;

import com.example.hy.recruitnew.util.StatusBarUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailActivity extends AppCompatActivity {

    private Unbinder mUnbinder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        StatusBarUtil.compat(this);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
