package com.zhjl.qihao.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * 作者： 黄郑宇
 * 时间： 2018/5/2
 * 类作用：自定义点击按钮识别时候的波纹
 */

public class WaveView extends AppCompatButton {

    private int waveColor = Color.parseColor("#298948");

    private int waveCount = 4;

    private Paint paint;

    private int mWidth;

    private int mHeight;

    private int centerX;

    private int centerY;

    private float radius;    // 最外圆半径，即最大半径

    private float innerRadius = 100;  // 最内圆的半径，即最小半径


    private float[] waveDegreeArr;

    private boolean isRunning = true;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(waveColor);
        paint.setStyle(Paint.Style.FILL);
        waveDegreeArr = new float[waveCount];



    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        centerX = mWidth / 2;
        centerY = mHeight / 2;
        radius = Math.min(mWidth, mHeight) / 2f;

        for (int i = 0; i < waveCount; i++) {
            waveDegreeArr[i] = innerRadius + (radius - innerRadius) / waveCount * i;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.UNSPECIFIED || widthMode == MeasureSpec.AT_MOST) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(dp2Px(120), MeasureSpec.EXACTLY);
        }
        if (heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(dp2Px(120), MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        drawWave(canvas);

        drawCenterCircle(canvas);

    }

    private void drawCenterCircle(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(centerX, centerY, innerRadius, paint);
    }

    private void drawWave(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < waveCount; i++) {
            paint.setAlpha((int) (255 - 255 * waveDegreeArr[i] / radius));
            canvas.drawCircle(centerX, centerY, waveDegreeArr[i], paint);
        }
        for (int i = 0; i < waveDegreeArr.length; i++) {
            if ((waveDegreeArr[i] += 4) > radius) {
                waveDegreeArr[i] = innerRadius;
            }
        }
        if (isRunning) {
            postInvalidateDelayed(50);
        }
    }


    private int dp2Px(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

}
