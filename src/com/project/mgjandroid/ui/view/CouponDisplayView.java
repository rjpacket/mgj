package com.project.mgjandroid.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.project.mgjandroid.R;


public class CouponDisplayView extends LinearLayout {

    private Paint mPaint;
    /**
     * 圆间距
     */
    private float gap;
    /**
     * 半径
     */
    private float radius;
    /**
     * 圆数量
     */
    private int circleNum;

    private float remain;


    public CouponDisplayView(Context context) {
        super(context, null);
    }

    public CouponDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CouponDisplayView);

        gap = a.getDimensionPixelSize(
                R.styleable.CouponDisplayView_gap, (int) getResources().getDimension(R.dimen.x4));

        radius = a.getDimensionPixelSize(
                R.styleable.CouponDisplayView_circle_radius, (int) getResources().getDimension(R.dimen.x4));
        a.recycle();

        int color = a.getColor(R.styleable.CouponDisplayView_circle_color, Color.WHITE);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (remain == 0){
            //计算不整除的剩余部分
            remain = (int) (w - gap) % (2 * radius + gap);
        }
        circleNum = (int) ((w - gap) / (2 * radius + gap));
    }


    public CouponDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < circleNum; i++){
            float x = gap + radius + remain / 2 + ((gap + radius * 2) * i);
            canvas.drawCircle(x, 0, radius, mPaint);
            canvas.drawCircle(x, getHeight(), radius, mPaint);
        }
    }
}
