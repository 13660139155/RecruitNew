package com.example.hy.recruitnew.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hy.recruitnew.DetailActivity;
import com.example.hy.recruitnew.R;
import com.example.hy.recruitnew.anim.CustomAnimation;
import com.example.hy.recruitnew.util.DisplayUtil;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 陈健宇 at 2018/11/9
 */
public class BaseRvAdapter extends RecyclerView.Adapter<BaseRvAdapter.ViewHolder> {

    // 动画转变线
    private float mTurnLinePosition;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private float mScreenHeight;

    public BaseRvAdapter(Context context) {
        mContext = context;
        mTurnLinePosition = 1000;
        mScreenHeight = DisplayUtil.getScreenHeight(mContext);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mRecyclerView = (RecyclerView) parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, null, false);
        ViewHolder holder = new ViewHolder(view);
        holder.ivLogo.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                DetailActivity.startActivityByShareMore(parent.getContext(), viewType, holder.ivLogo, holder.cd2);
            } else {
                DetailActivity.startActivity(parent.getContext(), viewType);
            }
        });
        holder.initListener(mRecyclerView, viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        switch (position) {
            case 0:
                holder.tvName.setText(R.string.main_rdc);
                holder.ivLogo.setImageResource(R.drawable.logo_rdc);
                holder.tvSome.setText(R.string.main_rdc_text);
                break;
            case 1:
                holder.tvName.setText(R.string.main_android);
                holder.ivLogo.setImageResource(R.drawable.logo_android);
                holder.tvSome.setText(R.string.main_android_text);
                break;
            case 2:
                holder.tvName.setText(R.string.main_background);
                holder.ivLogo.setImageResource(R.drawable.logo_background);
                holder.tvSome.setText(R.string.main_background_text);
                break;
            case 3:
                holder.tvName.setText(R.string.main_front);
                holder.ivLogo.setImageResource(R.drawable.logo_front);
                holder.tvSome.setText(R.string.main_front_text);
                break;
            case 4:
                holder.tvName.setText(R.string.main_bigData);
                holder.ivLogo.setImageResource(R.drawable.logo_bigdata);
                holder.tvSome.setText(R.string.main_bigData_text);
                break;
            default:
                holder.tvName.setText(R.string.main_bigData);
                holder.tvName.setVisibility(View.GONE);
                holder.cd1.setVisibility(View.GONE);
                holder.cd2.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.cd_1)
        CardView cd1;
        @BindView(R.id.tv_some)
        TextView tvSome;
        @BindView(R.id.cd_2)
        CardView cd2;
        @BindView(R.id.cl_container)
        ConstraintLayout clContainer;
        @BindView(R.id.root_view)
        RelativeLayout rootView;

        private CustomAnimation mCustomAnimation;
        private View mItemView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mItemView = view;
            mCustomAnimation = new CustomAnimation();
        }

        public void initListener(RecyclerView recyclerView, int position) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    assert manager != null;
                    int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();
                    int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                    if (firstVisibleItemPosition <= position && position <= lastVisibleItemPosition) {
                        float turnLinePosition = recyclerView.getHeight() / 2;
                        float scrollY = getScrollYDistance(recyclerView);
                        float itemHalf = getItemHalf(mItemView.getTop(), mItemView.getBottom());;
                        if(position == 0) itemHalf *= 2;
                        Log.d("rain", position + ": " + manager.findViewByPosition(position));
                        Log.d("rain", "recyclerviewTop: " + recyclerView.getTop() + ",top: " + mItemView.getTop() + ",bottom" + mItemView.getBottom());
                        Log.d("rain", "scrollY: " + scrollY);
                        Log.d("rain", "itemHalf: " + getItemHalf(mItemView.getTop(), mItemView.getBottom()));
                        Log.d("rain", "recyclerHeight: " + recyclerView.getHeight());
                        Log.d("rain", "turnPosition: " + turnLinePosition);
                        mCustomAnimation.setAnimByProcess(mItemView,  mCustomAnimation.getProcess(itemHalf, turnLinePosition, recyclerView.getHeight()));

                    }
                }
            });
        }

        /**
         * 获取item的顶部到Recyclerview可见区域的距离
         * @param scrollY    垂直滚动出Recyclerview可见区域的距离
         * @param itemHeight item高度
         * @param position   item位置
         */
        private float getItemTop(float scrollY, float itemHeight, float position) {
            return position * itemHeight - scrollY;
        }

        /**
         * 获取item的中部到Recyclerview可见区域的距离
         */
        private float getItemHalf(float top, float bottom) {
            return (bottom - top) / 2 + top;
        }



        /**
         * 获取垂直滚动出Recyclerview可见区域的距离
         */
        private float getScrollYDistance(RecyclerView recyclerView) {
            View firstVisibleItem = recyclerView.getChildAt(0);
            int itemHeight = firstVisibleItem.getMeasuredHeight();//得到item高度
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            assert manager != null;
            int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();//得到第一个item位置
            int firstVisibleBottom = manager.getDecoratedBottom(firstVisibleItem);//得到第一个item底部位置（距离父控件底部开始）
            return (firstVisibleItemPosition + 1) * itemHeight - firstVisibleBottom;
        }
    }

}
