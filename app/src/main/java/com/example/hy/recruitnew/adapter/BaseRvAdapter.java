package com.example.hy.recruitnew.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hy.recruitnew.DetailActivity;
import com.example.hy.recruitnew.R;
import com.example.hy.recruitnew.anim.CustomAnimation;

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
public class BaseRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // 动画转变线
    private RecyclerView mRecyclerView;
    private ItemClickListener mItemClickListener;
    private int mCompleteVisibileItemPosition = -1;

    public BaseRvAdapter() {}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mRecyclerView = (RecyclerView) parent;
        View view;
        if(viewType == 0){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.head, null, false);
            return new HeadHolder(view);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, null, false);
            ViewHolder holder = new ViewHolder(view);

            holder.tvSome.setOnClickListener(v -> {
                if(mItemClickListener == null) return;
                mItemClickListener.onClick(viewType);
            });

            holder.tvName.setOnClickListener(v -> {
                if(mItemClickListener == null) return;
                mItemClickListener.onClick(viewType);
            });

            holder.ivLogo.setOnClickListener(v -> {
                if(mItemClickListener != null && mCompleteVisibileItemPosition != viewType){
                    mItemClickListener.onClick(viewType);
                    return;
                }

                if(mItemClickListener != null)
                    mItemClickListener.onClick(viewType);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    DetailActivity.startActivityByShareMore(parent.getContext(), viewType - 1, holder.ivLogo, holder.cd2);
                } else {
                    DetailActivity.startActivity(parent.getContext(), viewType - 1);
                }
            });

            holder.initListener(mRecyclerView, viewType);

            return holder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof ViewHolder){
            ViewHolder holder = (ViewHolder)viewHolder;
            switch (position) {
                case 1:
                    holder.tvName.setText(R.string.main_rdc);
                    holder.ivLogo.setImageResource(R.drawable.logo_rdc);
                    holder.tvSome.setText(R.string.main_rdc_text);
                    break;
                case 2:
                    holder.tvName.setText(R.string.main_android);
                    holder.ivLogo.setImageResource(R.drawable.logo_android);
                    holder.tvSome.setText(R.string.main_android_text);
                    break;
                case 3:
                    holder.tvName.setText(R.string.main_background);
                    holder.ivLogo.setImageResource(R.drawable.logo_background);
                    holder.tvSome.setText(R.string.main_background_text);
                    break;
                case 4:
                    holder.tvName.setText(R.string.main_front);
                    holder.ivLogo.setImageResource(R.drawable.logo_front);
                    holder.tvSome.setText(R.string.main_front_text);
                    break;
                case 5:
                    holder.tvName.setText(R.string.main_bigData);
                    holder.ivLogo.setImageResource(R.drawable.logo_bigdata);
                    holder.tvSome.setText(R.string.main_bigData_text);
                    break;
                default:
                    break;
            }
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

    public void setItemClickListener(ItemClickListener itemClickListener){
        mItemClickListener = itemClickListener;
    }

    public interface ItemClickListener{
        void onClick(int postion);
    }

    class HeadHolder extends RecyclerView.ViewHolder{

        HeadHolder(@NonNull View itemView) {
            super(itemView);
        }
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

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mItemView = view;
            mCustomAnimation = new CustomAnimation();
        }

        void initListener(RecyclerView recyclerView, int position) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    if(newState == RecyclerView.SCROLL_STATE_IDLE)
                        mCompleteVisibileItemPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    assert manager != null;
                    int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();
                    int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                    if (firstVisibleItemPosition <= position && position <= lastVisibleItemPosition) {
                        float turnLinePosition = recyclerView.getHeight() / 2;
                        float itemHalf = getItemHalf(mItemView.getTop(), mItemView.getBottom());;
                        if(position == 0) itemHalf *= 2;
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
