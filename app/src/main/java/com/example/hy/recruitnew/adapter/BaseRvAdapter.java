package com.example.hy.recruitnew.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hy.recruitnew.DetailActivity;
import com.example.hy.recruitnew.R;
import com.example.hy.recruitnew.anim.CustomAnimation;
import com.example.hy.recruitnew.anim.TrunProcess;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 陈健宇 at 2018/11/9
 */
public class BaseRvAdapter extends RecyclerView.Adapter<BaseRvAdapter.ViewHolder> {

    // 动画转变线
    private float mTurningLine;
    private RecyclerView mRecyclerView;
    private Context mContext;

    public BaseRvAdapter(Context context) {
        mContext = context;
        mTurningLine = 300;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mRecyclerView = (RecyclerView)parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, null, false);
        ViewHolder holder = new ViewHolder(view);
        holder.ivLogo.setOnClickListener(v -> {
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
                DetailActivity.startActivityByShareMore(parent.getContext(), viewType, holder.ivLogo, holder.cd2);
            }else {
                DetailActivity.startActivity(parent.getContext(), viewType);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("rain", String.valueOf(position));
        switch (position){
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
                holder.tvName.setVisibility(View.INVISIBLE);
                holder.cd1.setVisibility(View.GONE);
                holder.cd2.setVisibility(View.GONE);
                break;
        }
       // holder.initListener(mRecyclerView, position);
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
        View mItemView;
        @BindView(R.id.root_view)
        ViewGroup mViewGroup;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        public void initListener(RecyclerView recyclerView, int position){
            float itemHight = 300;//item高度
            float recyclerHight = recyclerView.getMeasuredHeight();//rv总高度
            final float turningPart = recyclerHight - itemHight * 1.5f;
            float totalScroll = getItemCount() * itemHight - recyclerHight;//滑出屏幕的总距离
            Log.d("rain", "itemHeight: " + itemHight);
            Log.d("rain", "recyclerHight: " + recyclerHight);
            Log.d("rain", "totalScroll: " + totalScroll);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    float scrollY = getScrollYDistance(recyclerView);
                    float percent = scrollY / totalScroll;
                   // mTurningLine = scrollY * recyclerHight / totalScroll;//动画转变线
                    mTurningLine = itemHight + turningPart * percent;
                    Log.d("rain", "mTurningLine: "  + mTurningLine);
                    float itemTop = getItemTop(scrollY, itemHight, position);
                    int process = TrunProcess.getProcess(itemTop, mTurningLine, itemHight);
                    Log.d("rain", "process: " + process);
                    startAnim(mViewGroup, process);
                }
            });
        }

        private void startAnim(ViewGroup viewGroup, int process){
            CustomAnimation animation = new CustomAnimation();
            animation.setViewId(R.id.cl_container);
            animation.setAnimByProcess(viewGroup, process);
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
    }

}
