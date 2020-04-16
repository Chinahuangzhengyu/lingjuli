package com.zhjl.qihao.freshshop.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhjl.qihao.util.Utils;

public class MarginDecoration extends RecyclerView.ItemDecoration {

    private int margin;
    private Context context;
    private boolean isCenter;
    public MarginDecoration(Context context) {
        this.context = context;
        margin = Utils.dip2px(context,6);

    }

    public void setMargin(int margin) {
        isCenter = true;
        margin = Utils.dip2px(context,margin);
        this.margin = margin;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        //由于每行都只有2个，所以第一个都是2的倍数，把左边距设为0
        if (isCenter){
            if (parent.getChildLayoutPosition(view) % 2 == 0) {

                outRect.set(0, 0, margin, 0);

            } else {

                outRect.set(margin, 0, 0, 0);
            }
        }else {
            if (parent.getChildLayoutPosition(view) % 2 == 0) {

                outRect.set(margin, 0, 0, 0);

            } else {

                outRect.set(0, 0, margin, 0);
            }
        }
    }

}
