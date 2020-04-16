package com.zhjl.qihao.abrefactor.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class RoundViewpager extends ViewPager {

    private final RectF roundRect = new RectF();
    private final Paint maskPaint = new Paint();
    private final Paint zonePaint = new Paint();
    private float mRadius = 10;
    private boolean isOval = true;

    public RoundViewpager(@NonNull Context context) {
        super(context);
    }

    public RoundViewpager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        maskPaint.setAntiAlias(true);   //设置抗锯齿
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        zonePaint.setAntiAlias(true);
        float density = getResources().getDisplayMetrics().density;     //设置屏幕密度
        mRadius *= density;
    }

    public void setRadius(float radius) {
        this.mRadius = radius;
        invalidate();
    }

    /**
     * 是否为圆形
     */
    public void setOval(boolean isOval){
        this.isOval = isOval;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width = getWidth();
        int height = getHeight();
        if (!isOval) {//圆角ImageView
            roundRect.set(0, 0, width, height);
        } else {//圆形ImageView
            if (width > height) {
                left = width / 2 - height / 2;
                right = left + height;
                top = 0;
                bottom = height;
            } else {
                top = height / 2 - width / 2;
                bottom = top + width;
                left = 0;
                right = width;
            }
            roundRect.set(left, top, right, bottom);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);//保存现有的内容到一个offscreen bitmap中
        if (!isOval) {
            canvas.drawRoundRect(roundRect, mRadius, mRadius, zonePaint);//绘制一个与现在图片宽高一样的圆角矩形
        } else {
            canvas.drawOval(roundRect, zonePaint);
        }
        canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);//保存叠加后的内容
        super.draw(canvas);
        canvas.restore();//清空所有的图像矩阵修改状态
    }
}
