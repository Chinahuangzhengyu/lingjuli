package com.zhjl.qihao.abrefactor.view;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

public class ViewStyleSetter {
    private View mView;

    public ViewStyleSetter(View view) {
        this.mView = view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setRound(float radius) {
        this.mView.setClipToOutline(true);//用outline裁剪内容区域
        this.mView.setOutlineProvider(new RoundViewOutlineProvider(radius));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setOval() {
        this.mView.setClipToOutline(true);//用outline裁剪内容区域
        this.mView.setOutlineProvider(new OvalViewOutlineProvider());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void clearShapeStyle() {
        this.mView.setClipToOutline(false);
    }
}
