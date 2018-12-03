package com.example.hy.recruitnew.decoration;

import android.content.res.Resources;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 设置RecyclerView最后一个item的边距，使其居中
 * Created by 陈健宇 at 2018/12/2
 */
public class ItemDecoration extends RecyclerView.ItemDecoration {



    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int itemPosition = parent.getChildAdapterPosition(view);//获得当前itemPosition
        int itemCount = parent.getAdapter().getItemCount();//获得itemCount
        if(itemPosition == itemCount - 1){
            int recyclerHeight = parent.getHeight();
            int itemHeight = view.getHeight();
            if(itemHeight == 0){
               itemHeight = recyclerHeight / 2;
            }
            int bottom = (recyclerHeight - itemHeight) / 2;//居中
            outRect.set(0, 0, 0,
                    (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, bottom, parent.getContext().getResources().getDisplayMetrics())
            );
        }
    }
}
